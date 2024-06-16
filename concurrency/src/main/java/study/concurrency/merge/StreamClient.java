package study.concurrency.merge;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class StreamClient<E> implements Client<E> {
    private final List<Connection<E>> connections;

    public StreamClient(List<Connection<E>> connections) {
        this.connections = connections;
    }

    public List<E> getSorted() {
        return connections.stream()
                .map(Connection::findAll)
                .flatMap(List::stream)
                .sorted()
                .toList();
    }

    @Override
    public Future<List<E>> asyncGetSorted() {
        return CompletableFuture.supplyAsync(this::getSorted);
    }

    public void close() {
        connections.forEach(Connection::close);
    }
}
