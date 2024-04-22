package study.linear.queue;

public interface Queue<E> {
    void enqueue(E e);

    E dequeue();

    E peek();

    int size();

    boolean isEmpty();
}
