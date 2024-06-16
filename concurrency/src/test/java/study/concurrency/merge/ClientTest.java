package study.concurrency.merge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ClientTest {
    private Client<Integer> client;
    private List<Connection<Integer>> connections;

    @BeforeEach
    void setUp() {
        // Connection 개수: 7
        connections = List.of(
                new Connection<>(List.of(1, 3, 5)),
                new Connection<>(List.of(2, 4, 6)),
                new Connection<>(List.of(7, 8, 9)),
                new Connection<>(List.of(10, 15, 20)),
                new Connection<>(List.of(11, 12, 13)),
                new Connection<>(List.of(14, 16, 19)),
                new Connection<>(List.of(17, 18))
        );
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }

    @DisplayName("concurrentClient.asyncFindAll 메서드는 모든 Connection의 데이터를 비동기로 조회한다.")
    @Test
    void asyncFindAll_concurrentClient_success() throws ExecutionException, InterruptedException, TimeoutException {
        client = new ConcurrentClient<>(connections);

        Future<List<Integer>> future = client.asyncGetSorted();
        List<Integer> result = future.get(30, TimeUnit.SECONDS);
        assertThat(result)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }

    @DisplayName("parallelStreamClient.asyncFindAll 메서드는 모든 Connection의 데이터를 비동기로 조회한다.")
    @Test
    void asyncFindAll_parallelStreamClient_success() throws ExecutionException, InterruptedException, TimeoutException {
        client = new ParallelStreamClient<>(connections);

        Future<List<Integer>> future = client.asyncGetSorted();
        List<Integer> result = future.get(30, TimeUnit.SECONDS);
        assertThat(result)
                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }

    // Too slow to test
//    @DisplayName("streamClient.asyncFindAll 메서드는 모든 Connection의 데이터를 비동기로 조회한다.")
//    @Test
//    void asyncFindAll_streamClient_success() throws ExecutionException, InterruptedException, TimeoutException {
//        client = new StreamClient<>(connections);
//
//        Future<List<Integer>> future = client.asyncFindAll();
//        List<Integer> result = future.get(30, TimeUnit.SECONDS);
//        assertThat(result)
//                .containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
//    }
}
