package study.hash.consistent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KetamaConsistentHashTest {
    private ConsistentHash<String> consistentHash;

    @BeforeEach
    void setUp() {
        consistentHash = KetamaConsistentHash.<String>builder().virtualNodeNum(160).build();

        // add nodes
        consistentHash.insert("node1");
        consistentHash.insert("node2");
        consistentHash.insert("node3");
        consistentHash.insert("node4");
        consistentHash.insert("node5");
        consistentHash.insert("node6");
        consistentHash.insert("node7");
        consistentHash.insert("node8");
        consistentHash.insert("node9");
        consistentHash.insert("node10");
    }

    @DisplayName("노드가 정상 분배되었는지 테스트 - 성공")
    @Test
    void givenNodes_whenGet_thenReturnCorrectNode() {
        assertEquals("node7", consistentHash.get("key1"));
        assertEquals("node9", consistentHash.get("key2"));
        assertEquals("node6", consistentHash.get("key3"));
        assertEquals("node9", consistentHash.get("key4"));
        assertEquals("node9", consistentHash.get("key5"));
        assertEquals("node8", consistentHash.get("key6"));
        assertEquals("node4", consistentHash.get("key7"));
        assertEquals("node5", consistentHash.get("key8"));
        assertEquals("node10", consistentHash.get("key9"));
        assertEquals("node8", consistentHash.get("key10"));
    }
}
