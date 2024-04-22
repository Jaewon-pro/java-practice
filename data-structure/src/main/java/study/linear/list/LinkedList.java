package study.linear.list;

public class LinkedList<E> implements List<E> {
    private static class Node<E> {
        public E e;
        public Node<E> next;

        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }
    }

    private Node<E> head = null;
    private int size = 0;

    @Override
    public void add(E e) {
        size++;
        if (head == null) {
            head = new Node<>(e);
            return;
        }
        Node<E> cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new Node<>(e);
    }

    @Override
    public void add(int index, E e) {
        checkIndex(index);
        size++;
        if (index == 0) {
            head = new Node<>(e, head);
            return;
        }
        Node<E> prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        prev.next = new Node<>(e, prev.next);
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        Node<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    @Override
    public void set(int index, E e) {
        checkIndex(index);
        Node<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        size--;
        Node<E> prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node<E> ret = prev.next;
        prev.next = ret.next;
        ret.next = null;
        return ret.e;
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) != -1;
    }

    @Override
    public int indexOf(E e) {
        if (isEmpty()) {
            return -1;
        }
        Node<E> cur = head;
        for (int i = 0; cur != null; i++) {
            if (cur.e.equals(e)) {
                return i;
            }
            cur = cur.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        for (Node<E> cur = head; cur != null; ) {
            Node<E> next = cur.next;
            cur.next = null;
            cur = next;
        }
        head = null;
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Index " + index + " is illegal.");
        }
    }
}
