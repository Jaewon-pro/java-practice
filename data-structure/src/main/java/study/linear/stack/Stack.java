package study.linear.stack;

public class Stack<E> {
    private E[] data;

    private int size;

    public Stack(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    public Stack() {
        this(10);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(E e) {
        if (size == data.length) {
            throw new IllegalStateException("Stack is full.");
        }
        data[size++] = e;
    }

    public E top() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return data[size - 1];
    }

    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return data[--size];
    }
}
