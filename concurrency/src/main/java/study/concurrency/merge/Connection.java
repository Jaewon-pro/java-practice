package study.concurrency.merge;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Connection class represents a connection to a data source (e.g. database, server, etc.)
 * Simulates slow query + network latency
 * @param <T> type of data
 */
public class Connection<T> implements Closeable {
    private final List<T> data;

    public Connection(List<T> data) {
        this.data = data.stream().sorted().toList();
    }

    public void close() {
        // do nothing
    }

    public List<T> findAll() {
        try {
            // simulate slow query + network latency
            Thread.sleep(100 + (long) (Math.random() * 10));
        } catch (InterruptedException ignored) {
        }
        return new ArrayList<>(data);
    }

    public CompletableFuture<List<T>> asyncFindAll() {
        return CompletableFuture.supplyAsync(this::findAll);
    }
}
