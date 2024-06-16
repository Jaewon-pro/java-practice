package study.concurrency.merge;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Client interface defines the contract for getting elements data from somewhere (e.g. database, server, etc.)
 *
 * @param <T>
 */
public interface Client<T> extends Closeable {
    /**
     * Get all sorted elements data that client can provide
     *
     * @return sorted elements
     */
    List<T> getSorted();

    /**
     * Get all sorted elements data that client can provide asynchronously
     *
     * @return future with sorted elements
     */
    Future<List<T>> asyncGetSorted();
}
