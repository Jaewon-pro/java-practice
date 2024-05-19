package study.graph;

import java.util.LinkedList;
import java.util.Queue;

public final class GraphTraversal {
    private GraphTraversal() {
    }

    private record Vertex(
            int vertex,
            int cost
    ) {
    }

    /**
     * Breadth-first search
     * Graph should be non-weighted
     *
     * @param graph  Graph
     * @param source Source vertex
     * @return Costs
     */
    public static int bfs(Graph graph, int source, int destination) {
        boolean[] visited = new boolean[graph.V()];
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(new Vertex(source, 0));
        visited[source] = true;
        while (!queue.isEmpty()) {
            Vertex v = queue.poll();
            if (v.vertex == destination) {
                return v.cost;
            }
            for (int w : graph.adj(v.vertex)) {
                if (!visited[w]) {
                    queue.add(new Vertex(w, v.cost + 1));
                    visited[w] = true;
                }
            }
        }
        return Graph.Edge.INFINITY;
    }
}
