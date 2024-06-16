package study.concurrency.merge;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * ParallelStreamClient uses parallel stream to get all elements data from connections.
 * <br><br>
 * Using parallel stream can improve performance by utilizing multiple CPU cores.
 * <br>
 * But it sorts after all elements are collected. This can be slower than merging while collecting.
 *
 * @param <E>
 */
public class ParallelStreamClient<E> implements Client<E> {
    private final List<Connection<E>> connections;

    public ParallelStreamClient(List<Connection<E>> connections) {
        this.connections = connections;
    }

    public List<E> getSorted() {
        return connections.parallelStream()
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
