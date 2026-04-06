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

        for (int i = 0; i < n; i++) {
            System.out.println("Distance to " + i + " = " + dist[i]);
        }
    }

    static void floydWarshall(List<List<Edge>> graph) {
        int n = graph.size();
        int[][] dist = new int[n][n];

        // Step 1: initialize matrix
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        // Step 2: fill from adjacency list
        for (int i = 0; i < n; i++) {
            for (Edge e : graph.get(i)) {
                dist[i][e.to] = e.weight;
            }
        }

        // Step 3: main algorithm
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

        // Step 4: print result
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == Integer.MAX_VALUE)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + " ");
            }
            System.out.println();
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

    public static void main(String[] args) {
        List<List<Edge>> graph;
        graph = createSparseGraph(10);
        List<List<Edge>> graph2;
        graph2 = createDenseGraph(10);
        dijkstra(graph, 0);
        System.out.println();
        dijkstra(graph2, 0);
        System.out.println();
        floydWarshall(graph);
        System.out.println();
        floydWarshall(graph2);
    }
}
