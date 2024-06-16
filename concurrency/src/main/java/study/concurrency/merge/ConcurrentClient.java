package study.concurrency.merge;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

/**
 * ConcurrentClient is a client that uses multiple threads to get data from multiple connections
 * @param <E>
 */
@Slf4j
public class ConcurrentClient<E extends Comparable<E>> implements Client<E> {
    private final List<Connection<E>> connections;
    private final ExecutorService executorService;
    private E[] merged;

    public ConcurrentClient(List<Connection<E>> connections) {
        this.connections = connections;
        this.executorService = Executors.newFixedThreadPool(connections.size() * 2 + 1);
    }

    public List<E> getSorted() {
        Future<List<E>> future = asyncGetSorted();
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted");
        } catch (ExecutionException e) {
            log.error("Execution failed", e);
        }
        throw new RuntimeException("Failed to get result");
    }

    public Future<List<E>> asyncGetSorted() {
        BlockingQueue<List<E>> queue = new LinkedBlockingQueue<>(connections.size());
        connections.forEach(conn -> executorService.submit(() -> {
            boolean ok = queue.offer(conn.findAll());
            if (!ok) {
                log.error("Failed to offer results to the queue");
                throw new RuntimeException("Failed to offer results to the queue");
            }
        }));

        int tasksCount = connections.size();
        CountDownLatch latch = new CountDownLatch(tasksCount);
        E[] arrayTemplate = (E[]) new Comparable[0];

        for (int i = 0; i < tasksCount; i++) {
            executorService.execute(() -> {
                try {
                    List<E> value = queue.take();
                    synchronized (this) {
                        merged = merge(merged, value.toArray(arrayTemplate));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.warn("Thread was interrupted while waiting to take from the queue", e);
                } finally {
                    latch.countDown();
                }
            });
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                latch.await();
                var result = merged;
                merged = null;
                return List.of(result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Await on latch was interrupted", e);
                return null;
            }
        }, executorService);
    }

    public void close() {
        connections.forEach(Connection::close);
        executorService.shutdown();
    }

    private E[] merge(E[] v1, E[] v2) {
        int i = 0, j = 0, resIdx = 0;
        if (v1 == null) {
            return v2;
        }
        @SuppressWarnings("unchecked")
        E[] result = (E[]) new Comparable[v1.length + v2.length];

        while (i < v1.length && j < v2.length) {
            if (v1[i].compareTo(v2[j]) < 0) {
                result[resIdx++] = v1[i++];
            } else {
                result[resIdx++] = v2[j++];
            }
        }
        while (i < v1.length) {
            result[resIdx++] = v1[i++];
        }
        while (j < v2.length) {
            result[resIdx++] = v2[j++];
        }
        return result;
    }
}
