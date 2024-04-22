package study.linear.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularQueueTest {
    private Queue<Integer> queue;

    @BeforeEach
    void setUp() {
        queue = new CircularQueue<>(5);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
    }

    @Test
    void enqueue() {
        assertEquals(5, queue.size());
        queue.enqueue(6);
        assertEquals(6, queue.size());
    }

    @Test
    void dequeue() {
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
        assertEquals(4, queue.dequeue());
        assertEquals(5, queue.dequeue());
        assertNull(queue.dequeue());
    }

    @Test
    void peek() {
        assertEquals(1, queue.peek());
        queue.dequeue();
        assertEquals(2, queue.peek());
    }

    @Test
    void size() {
        assertEquals(5, queue.size());
        queue.dequeue();
        queue.dequeue();
        assertEquals(3, queue.size());
    }

    @Test
    void isEmpty() {
        assertFalse(queue.isEmpty());
        queue.dequeue();
        for (int i = 0; i < 4; i++) {
            queue.dequeue();
        }
        assertTrue(queue.isEmpty());
    }
}
