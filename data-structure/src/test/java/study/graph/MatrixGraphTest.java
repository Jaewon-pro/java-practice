package study.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixGraphTest {
    private Graph graph;

    @BeforeEach
    void setUp() {
        graph = new MatrixGraph(6);
        // Vertex 5 is not connected to any other vertex
        graph.addEdge(new Graph.Edge(0, 1, 1));
        graph.addEdge(new Graph.Edge(0, 2, 1));
        graph.addEdge(new Graph.Edge(0, 4, 1));
        graph.addEdge(new Graph.Edge(1, 2, 1));
        graph.addEdge(new Graph.Edge(2, 3, 1));
        graph.addEdge(new Graph.Edge(2, 4, 1));
        graph.addEdge(new Graph.Edge(3, 4, 1));
    }

    @DisplayName("Graph should has correct adjacent vertices")
    @Test
    void adj() {
        assertThat(StreamSupport.stream(
                graph.adj(0).spliterator(), false).toList()).isEqualTo(List.of(1, 2, 4));
        assertThat(StreamSupport.stream(
                graph.adj(1).spliterator(), false).toList()).isEqualTo(List.of(0, 2));
        assertThat(StreamSupport.stream(
                graph.adj(2).spliterator(), false).toList()).isEqualTo(List.of(0, 1, 3, 4));
        assertThat(StreamSupport.stream(
                graph.adj(3).spliterator(), false).toList()).isEqualTo(List.of(2, 4));
        assertThat(StreamSupport.stream(
                graph.adj(4).spliterator(), false).toList()).isEqualTo(List.of(0, 2, 3));
        assertThat(StreamSupport.stream(
                graph.adj(5).spliterator(), false).toList()).isEqualTo(List.of());
    }

    @Test
    void degree() {
        assertEquals(3, graph.degree(0));
        assertEquals(2, graph.degree(1));
        assertEquals(4, graph.degree(2));
        assertEquals(2, graph.degree(3));
        assertEquals(3, graph.degree(4));
    }

    @DisplayName("Graph should has correct edges")
    @Test
    void checkGraph() {
        assertEquals(6, graph.V());
        assertTrue(graph.hasEdge(new Graph.Edge(0, 1, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(0, 2, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(0, 4, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(1, 2, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(2, 3, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(2, 4, 1)));
        assertTrue(graph.hasEdge(new Graph.Edge(3, 4, 1)));
    }
}
