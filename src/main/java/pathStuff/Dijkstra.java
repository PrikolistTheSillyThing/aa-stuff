package pathStuff;

import java.util.*;

class Edge {
    int to;
    int weight;

    Edge(int t, int w) {
        to = t;
        weight = w;
    }
}

public class Dijkstra {

    static void dijkstra(List<List<Edge>> graph, int start) {
        int n = graph.size();

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int currentDist = current[1];

            if (currentDist > dist[node]) continue;

            for (Edge e : graph.get(node)) {
                int next = e.to;
                int newDist = dist[node] + e.weight;

                if (newDist < dist[next]) {
                    dist[next] = newDist;
                    pq.add(new int[]{next, newDist});
                }
            }
        }

    }

    static void floydWarshall(List<List<Edge>> graph) {
        int n = graph.size();
        int[][] dist = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (Edge e : graph.get(i)) {
                dist[i][e.to] = e.weight;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (dist[i][k] == Integer.MAX_VALUE || dist[k][j] == Integer.MAX_VALUE)
                        continue;

                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

    }

    static List<List<Edge>> createSparseGraph(int n) {
        List<List<Edge>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            int edges = rand.nextInt(2) + 1; // 1–2 edges per node

            for (int j = 0; j < edges; j++) {
                int to = rand.nextInt(n);
                int weight = rand.nextInt(9) + 1;

                if (to != i) {
                    graph.get(i).add(new Edge(to, weight));
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
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int weight = rand.nextInt(9) + 1;
                    graph.get(i).add(new Edge(j, weight));
                }
            }
        }

        return graph;
    }

    static long testDijkstra(List<List<Edge>> graph) {
        long start = System.nanoTime();

        for (int i = 0; i < graph.size(); i++) {
            dijkstra(graph, i);
        }

        long end = System.nanoTime();
        return end - start;
    }

    static long avgTestDijkstra(List<List<Edge>> graph, int runs) {
        long total = 0;

        for (int i = 0; i < runs; i++) {
            total += testDijkstra(graph);
        }

        return total / runs;
    }

    static long testFloyd(List<List<Edge>> graph) {
        long start = System.nanoTime();

        floydWarshall(graph);

        long end = System.nanoTime();
        return end - start;
    }

    static long avgTestFloyd(List<List<Edge>> graph, int runs) {
        long total = 0;

        for (int i = 0; i < runs; i++) {
            total += testFloyd(graph);
        }

        return total / runs;
    }

    public static void main(String[] args) {
        int[] sizes = {50, 100, 200};

        for (int n : sizes) {
            System.out.println("n = " + n);

            List<List<Edge>> sparse = createSparseGraph(n);
            List<List<Edge>> dense = createDenseGraph(n);

            for (int i = 0; i < 5; i++) {
                testDijkstra(sparse);
                testFloyd(sparse);
            }

            long dSparse = avgTestDijkstra(sparse, 5);
            long fSparse = avgTestFloyd(sparse, 5);

            long dDense = testDijkstra(dense);
            long fDense = testFloyd(dense);


            System.out.println("Sparse - Dijkstra: " + dSparse);
            System.out.println("Sparse - Floyd: " + fSparse);
            System.out.println("Dense  - Dijkstra: " + dDense);
            System.out.println("Dense  - Floyd: " + fDense);

            System.out.println();
        }
    }
}
