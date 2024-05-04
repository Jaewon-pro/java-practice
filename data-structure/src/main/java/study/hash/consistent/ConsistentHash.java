package study.hash.consistent;

public interface ConsistentHash<E> {
    void insert(E node);

    void remove(E node);

    E get(Object key);
}
