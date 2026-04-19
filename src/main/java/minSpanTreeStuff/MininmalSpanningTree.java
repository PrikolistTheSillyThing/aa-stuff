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

    static void main(String[] args) {
        int[] sizes = {50, 100, 200};

        double[] pSparseArr = new double[sizes.length];
        double[] pDenseArr = new double[sizes.length];

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

            System.out.println("Sparse - Prim: " + pSparse);
            System.out.println("Dense  - Prim: " + pDense);

            System.out.println();

            pSparseArr[s] = pSparse / 1_000_000_000.0;
            pDenseArr[s] = pDense / 1_000_000_000.0;
        }

    }
}
