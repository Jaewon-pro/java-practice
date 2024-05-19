package study.graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ListGraph implements Graph {
    private final Map<Integer, Set<Integer>> adj = new TreeMap<>();
    private final int V;
    private boolean isDirected;

    public ListGraph(int V) {
        this.V = V;
        for (int i = 0; i < V; ++i) {
            adj.put(i, new HashSet<>());
        }
    }

    @Override
    public void addEdge(Edge edge) {
        adj.get(edge.from()).add(edge.to());
        if (!isDirected) {
            adj.get(edge.to()).add(edge.from());
        }
    }

    @Override
    public void removeEdge(Edge edge) {
        adj.get(edge.from()).remove(edge.to());
        if (!isDirected) {
            adj.get(edge.to()).remove(edge.from());
        }
    }

    @Override
    public boolean hasEdge(Edge edge) {
        if (isDirected) {
            return adj.get(edge.from()).contains(edge.to());
        }
        return adj.get(edge.from()).contains(edge.to())
                && adj.get(edge.to()).contains(edge.from());
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adj.get(v);
    }

    @Override
    public int degree(int v) {
        return adj.get(v).size();
    }

    @Override
    public int V() {
        return V;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < V; ++v) {
            sb.append(v).append(": ");
            for (int w : adj.get(v)) {
                sb.append(w).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
