package study.graph;

import java.util.Arrays;
import java.util.stream.IntStream;

public class MatrixGraph implements Graph {
    private final int[][] graph;
    private final int V;
    private int E;
    private boolean isDirected;

    public MatrixGraph(int V) {
        this.V = V;
        this.E = 0;
        this.graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            Arrays.fill(graph[i], Edge.INFINITY);
        }
        this.isDirected = false;
    }

    public MatrixGraph(int V, boolean isDirected) {
        this(V);
        this.isDirected = isDirected;
    }

    @Override
    public void addEdge(Edge edge) {
        if (hasEdge(edge)) {
            return;
        }
        graph[edge.from()][edge.to()] = edge.weight();
        if (!isDirected) {
            graph[edge.to()][edge.from()] = edge.weight();
        }
        E++;
    }

    @Override
    public void removeEdge(Edge edge) {
        if (!hasEdge(edge)) {
            return;
        }
        graph[edge.from()][edge.to()] = Edge.INFINITY;
        if (!isDirected) {
            graph[edge.to()][edge.from()] = Edge.INFINITY;
        }
        E--;
    }

    @Override
    public boolean hasEdge(Edge edge) {
        if (isDirected) {
            return graph[edge.from()][edge.to()] != Edge.INFINITY;
        }
        return graph[edge.from()][edge.to()] != Edge.INFINITY
                && graph[edge.to()][edge.from()] != Edge.INFINITY;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return () -> IntStream.range(0, V).filter(i -> graph[v][i] != Edge.INFINITY).boxed().iterator();
    }

    @Override
    public int degree(int v) {
        return (int) IntStream.of(graph[v]).filter(w -> w != Edge.INFINITY).count();
    }

    @Override
    public int V() {
        return V;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < V; i++) {
            sb.append(i).append(": ");
            for (int j = 0; j < V; j++) {
                sb.append(graph[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return "MatrixGraph{" +
                "graph=" + Arrays.deepToString(graph) +
                ", V=" + V +
                ", E=" + E +
                "}\n" + sb;
    }
}
