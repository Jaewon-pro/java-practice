package study;

import org.openjdk.jmh.annotations.*;
import study.concurrency.merge.Client;
import study.concurrency.merge.ConcurrentClient;
import study.concurrency.merge.Connection;
import study.concurrency.merge.ParallelStreamClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MergeBenchmark {
    private static final List<Connection<Slow>> connections = List.of(
            new Connection<>(Slow.of(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)),
            new Connection<>(Slow.of(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)),
            new Connection<>(Slow.of(21, 23, 25, 27, 29, 31, 33, 35, 37, 39)),
            new Connection<>(Slow.of(22, 24, 26, 28, 30, 32, 34, 36, 38, 40)),
            new Connection<>(Slow.of(41, 43, 45, 47, 49, 51, 53, 55, 57, 59)),
            new Connection<>(Slow.of(42, 44, 46, 48, 50, 52, 54, 56, 58, 60)),
            new Connection<>(Slow.of(61, 63, 65, 67, 69, 71, 73, 75, 77, 79)),
            new Connection<>(Slow.of(62, 64, 66, 68, 70, 72, 74, 76, 78, 80)),
            new Connection<>(Slow.of(81, 83, 85, 87, 89, 91, 93, 95, 97, 99)),
            new Connection<>(Slow.of(82, 84, 86, 88, 90, 92, 94, 96, 98, 100))
    );

    private static final Client<Slow> concurrentClient = new ConcurrentClient<>(connections);
    private static final Client<Slow> parallelStreamClient = new ParallelStreamClient<>(connections);
//    private final Client<Slow> streamClient = new StreamClient<>(connections); <- Too slow to benchmark

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
        concurrentClient.close();
        parallelStreamClient.close();
    }

    @Benchmark
    public List<Slow> asyncFindAll_concurrentClient() {
        return concurrentClient.getSorted();
    }

    @Benchmark
    public List<Slow> asyncFindAll_parallelStreamClient() {
        return parallelStreamClient.getSorted();
    }

//    @Benchmark <- Too slow to benchmark
//    public List<String> asyncFindAll_streamClient() {
//        return streamClient.findAll();
//    }

    /**
     * Slow comparing class for benchmarking
     */
    public static class Slow implements Comparable<Slow> {
        private final int value;

        private Slow(int value) {
            this.value = value;
        }

        public static Slow of(int value) {
            return new Slow(value);
        }

        public static List<Slow> of(int... values) {
            return Arrays.stream(values).mapToObj(Slow::of).toList();
        }

        @Override
        public int compareTo(Slow o) {
            try {
                // simulate slow comparison
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return Integer.compare(value, o.value);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
