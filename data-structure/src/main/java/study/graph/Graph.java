package study.graph;

public interface Graph {
    record Edge(
            int from,
            int to,
            int weight
    ) {
        public final static int INFINITY = Integer.MAX_VALUE;
    }

    void addEdge(Edge edge);

    void removeEdge(Edge edge);

    boolean hasEdge(Edge edge);

    Iterable<Integer> adj(int v);

    int degree(int v);
    int V();
}
