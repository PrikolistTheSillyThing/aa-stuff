package searchingStuff;

import java.util.*;

public class Graph {

    private int vertices;
    private LinkedList<Integer>[] adjList;

    Graph(int v) {
        vertices = v;
        adjList = new LinkedList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    void addEdge(int v, int w) {
        adjList[v].add(w);
    }

    void bfs(int start) {

        boolean[] visited = new boolean[vertices];

        Queue<Integer> queue = new LinkedList<>();

        int vertexCount = 0;
        int edgeCount = 0;

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {

            int node = queue.poll();
            vertexCount++;
            System.out.print(node + " ");

            for (int neighbor : adjList[node]) {
                edgeCount++;
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        System.out.println();

        System.out.println("Visited vertices: " + vertexCount);
        System.out.println("Processed edges: " + edgeCount);
    }

    void dfs(int start) {
        boolean[] visited = new boolean[vertices];

        int[] vertexCount = {0};
        int[] edgeCount = {0};

        dfsVisit(start, visited, vertexCount, edgeCount);

        System.out.println();
        System.out.println("Visited vertices: " + vertexCount[0]);
        System.out.println("Processed edges: " + edgeCount[0]);
    }

    void dfsVisit(int node, boolean[] visited, int[] vertexCount, int[] edgeCount) {

        visited[node] = true;
        System.out.print(node + " ");

        vertexCount[0]++;

        for (int neighbor : adjList[node]) {
            edgeCount[0]++;

            if (!visited[neighbor]) {
                dfsVisit(neighbor, visited, vertexCount, edgeCount);
            }
        }
    }

    static void display(Graph g) {
        System.out.println("BFS traversal starting from node 0:");

        g.bfs(0);

        System.out.println();

        System.out.println("DFS traversal starting from node 0:");

        g.dfs(0);

        System.out.println();
    }

    public static void main(String[] args) {

        Graph g = new Graph(6);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);

        display(g);

        Graph g2 = new Graph(1000); //sparse directed graph, tree

        for (int i = 0; i < 500; i++) {
            int left  = 2 * i + 1;
            int right = 2 * i + 2;

            if (left  < 1000) g2.addEdge(i, left);
            if (right < 1000) g2.addEdge(i, right);
        }

        display(g2);

        Random rand = new Random();

        Graph g3 = new Graph(1000); //dense graph

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (i != j) {
                    g3.addEdge(i, j);
                }
            }
        }

        display(g3);

    }
}
