package study.linear.list;

public interface List<E> {
    void add(E e);

    void add(int index, E e);

    E get(int index);

    void set(int index, E e);

    E remove(int index);

    boolean contains(E e);

    int indexOf(E e);

    int size();

    boolean isEmpty();

    void clear();
}
