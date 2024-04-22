package study.linear.queue;

public class CircularQueue<E> implements Queue<E> {
    private int capacity;
    private int front = 0;
    private int rear = 0;
    private int size = 0;
    private E[] data;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        data = (E[]) new Object[capacity];
    }

    @Override
    public void enqueue(E e) {
        if (size == capacity - 1) {
            resize(capacity * 2);
        }
        size++;
        data[rear] = e;
        rear = (rear + 1) % capacity;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        size--;
        E ret = data[front];
        front = (front + 1) % capacity;
        return ret;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++) {
            newData[i] = data[(i + front) % capacity];
        }
        data = newData;
        front = 0;
        rear = size();
        capacity = newCapacity;
    }
}
