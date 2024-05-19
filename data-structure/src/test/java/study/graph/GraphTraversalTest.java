package study.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static study.graph.GraphTraversal.bfs;

class GraphSearchTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new MatrixGraph(6);
        // Vertex 5 is not connected to any other vertex
        // Consider all edges as undirected, no weight
        graph.addEdge(new Graph.Edge(0, 1, 1));
        graph.addEdge(new Graph.Edge(0, 2, 1));
        graph.addEdge(new Graph.Edge(0, 4, 1));
        graph.addEdge(new Graph.Edge(1, 2, 1));
        graph.addEdge(new Graph.Edge(2, 3, 1));
        graph.addEdge(new Graph.Edge(2, 4, 1));
        graph.addEdge(new Graph.Edge(3, 4, 1));
    }

    @DisplayName("BFS Graph traversal result should be correct")
    @Test
    void BFS() {
        assertEquals(0, bfs(graph, 0, 0));
        assertEquals(1, bfs(graph, 0, 2));
        assertEquals(2, bfs(graph, 1, 4));
        assertEquals(2, bfs(graph, 0, 3));
        assertEquals(Graph.Edge.INFINITY, bfs(graph, 0, 5));
    }
}
