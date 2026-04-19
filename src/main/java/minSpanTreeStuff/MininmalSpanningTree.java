package minSpanTreeStuff;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.text.DecimalFormat;

import java.util.*;

class Edge {
    int to;
    int weight;

    Edge(int t, int w) {
        to = t;
        weight = w;
    }
}

class KEdge {
    int u, v, weight;

    KEdge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.weight = w;
    }
}

class DSU {
    int[] parent, rank;

    DSU(int n) {
        parent = new int[n];
        rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) return false;

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }

        return true;
    }
}

class MinimalSpanningTree {
    static int prim(List<List<Edge>> graph) {
        int n = graph.size();

        boolean[] visited = new boolean[n];
        int[] minEdge = new int[n];

        Arrays.fill(minEdge, Integer.MAX_VALUE);
        minEdge[0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{0, 0});

        int totalWeight = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];

            if (visited[node]) continue;

            visited[node] = true;
            totalWeight += current[1];

            for (Edge e : graph.get(node)) {
                if (!visited[e.to] && e.weight < minEdge[e.to]) {
                    minEdge[e.to] = e.weight;
                    pq.add(new int[]{e.to, e.weight});
                }
            }
        }

        return totalWeight;
    }

    static long testPrim(List<List<Edge>> graph) {
        long start = System.nanoTime();

        prim(graph);

        long end = System.nanoTime();
        return end - start;
    }

    static long avgTestPrim(List<List<Edge>> graph, int runs) {
        long total = 0;

        for (int i = 0; i < runs; i++) {
            total += testPrim(graph);
        }

        return total / runs;
    }

    static List<List<Edge>> createSparseGraph(int n) {
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            int edges = rand.nextInt(2) + 1;

            for (int j = 0; j < edges; j++) {
                int to = rand.nextInt(n);
                int weight = rand.nextInt(9) + 1;

                if (to != i) {
                    graph.get(i).add(new Edge(to, weight));
                    graph.get(to).add(new Edge(i, weight));
                }
            }
        }

        return graph;
    }

    static List<List<Edge>> createDenseGraph(int n) {
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = rand.nextInt(9) + 1;

                graph.get(i).add(new Edge(j, weight));
                graph.get(j).add(new Edge(i, weight));
            }
        }

        return graph;
    }

    static List<KEdge> getAllEdges(List<List<Edge>> graph) {
        List<KEdge> edges = new ArrayList<>();
        int n = graph.size();

        for (int i = 0; i < n; i++) {
            for (Edge e : graph.get(i)) {
                if (i < e.to) { // avoid duplicates
                    edges.add(new KEdge(i, e.to, e.weight));
                }
            }
        }

        return edges;
    }

    static int kruskal(List<List<Edge>> graph) {
        int n = graph.size();

        List<KEdge> edges = getAllEdges(graph);

        edges.sort(Comparator.comparingInt(e -> e.weight));

        DSU dsu = new DSU(n);

        int totalWeight = 0;
        int edgesUsed = 0;

        for (KEdge e : edges) {
            if (dsu.union(e.u, e.v)) {
                totalWeight += e.weight;
                edgesUsed++;

                if (edgesUsed == n - 1) break;
            }
        }

        return totalWeight;
    }

    static long testKruskal(List<List<Edge>> graph) {
        long start = System.nanoTime();

        kruskal(graph);

        long end = System.nanoTime();
        return end - start;
    }

    static long avgTestKruskal(List<List<Edge>> graph, int runs) {
        long total = 0;

        for (int i = 0; i < runs; i++) {
            total += testKruskal(graph);
        }

        return total / runs;
    }

    static void main(String[] args) {
        int[] sizes = {50, 100, 200};

        double[] pSparseArr = new double[sizes.length];
        double[] pDenseArr = new double[sizes.length];
        double[] kSparseArr = new double[sizes.length];
        double[] kDenseArr = new double[sizes.length];

        for (int s = 0; s < sizes.length; s++) {
            int n = sizes[s];
            System.out.println("n = " + n);

            List<List<minSpanTreeStuff.Edge>> sparse = createSparseGraph(n);
            List<List<minSpanTreeStuff.Edge>> dense = createDenseGraph(n);

            // warm-up
            for (int i = 0; i < 5; i++) {
                testPrim(sparse);
            }

            long pSparse = avgTestPrim(sparse, 5);
            long pDense = avgTestPrim(dense, 5);
            long kSparse = avgTestKruskal(sparse, 5);
            long kDense = avgTestKruskal(dense, 5);

            System.out.println("Sparse - Prim: " + pSparse);
            System.out.println("Sparse - Kruskal: " + kSparse);
            System.out.println("Dense  - Prim: " + pDense);
            System.out.println("Dense  - Kruskal: " + kDense);

            System.out.println();

            pSparseArr[s] = pSparse / 1_000_000_000.0;
            pDenseArr[s] = pDense / 1_000_000_000.0;
            kSparseArr[s] = kSparse / 1_000_000_000.0;
            kDenseArr[s] = kDense / 1_000_000_000.0;
        }

        XYSeries pSparseSeries = new XYSeries("Sparse - Prim");
        XYSeries kSparseSeries = new XYSeries("Sparse - Kruskal");
        XYSeries pDenseSeries = new XYSeries("Dense - Prim");
        XYSeries kDenseSeries = new XYSeries("Dense - Kruskal");

        for (int i = 0; i < sizes.length; i++) {
            pSparseSeries.add(sizes[i], pSparseArr[i]);
            kSparseSeries.add(sizes[i], kSparseArr[i]);
            pDenseSeries.add(sizes[i], pDenseArr[i]);
            kDenseSeries.add(sizes[i], kDenseArr[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(pSparseSeries);
        dataset.addSeries(kSparseSeries);
        dataset.addSeries(pDenseSeries);
        dataset.addSeries(kDenseSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Prim vs Kruskal",
                "Number of Vertices (n)",
                "Time (seconds)",
                dataset
        );

        NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        yAxis.setNumberFormatOverride(new DecimalFormat("0.000000"));

        JFrame frame = new JFrame("MST Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
