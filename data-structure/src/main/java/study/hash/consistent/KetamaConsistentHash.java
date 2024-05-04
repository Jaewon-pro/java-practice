package study.hash.consistent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Ketama Consistent Hashing
 *
 * @param <E>
 */
public class KetamaConsistentHash<E> implements ConsistentHash<E> {
    private final SortedMap<Integer, E> hashRing = new TreeMap<>();
    private final int virtualNodeNum;
    private final HashAlgorithm hashAlgorithm;

    public static <U> Builder<U> builder() {
        return new Builder<>();
    }

    public static class Builder<E> {
        private int virtualNodeNum = 160;
        private HashAlgorithm hashAlgorithm = HashAlgorithm.MD5;

        public Builder<E> virtualNodeNum(int virtualNodeNum) {
            this.virtualNodeNum = virtualNodeNum;
            return this;
        }

        public Builder<E> hashAlgorithm(HashAlgorithm hashAlgorithm) {
            this.hashAlgorithm = hashAlgorithm;
            return this;
        }

        public KetamaConsistentHash<E> build(Collection<E> nodes) {
            return new KetamaConsistentHash<>(virtualNodeNum, hashAlgorithm, nodes);
        }

        public KetamaConsistentHash<E> build() {
            return new KetamaConsistentHash<>(virtualNodeNum, hashAlgorithm, Collections.emptyList());
        }
    }

    public KetamaConsistentHash(int virtualNodeNum, HashAlgorithm hashAlgorithm, Collection<E> nodes) {
        this.virtualNodeNum = virtualNodeNum;
        this.hashAlgorithm = hashAlgorithm;
        for (E node : nodes) {
            insert(node);
        }
    }

    @Override
    public void insert(E node) {
        for (int i = 0; i < virtualNodeNum / 4; i++) {
            // digest is 16 length byte array
            byte[] digest = hashAlgorithm.hash(node.hashCode() + "-" + i);
            for (int j = 0; j < 4; j++) {
                int offset = j * 4;
                int hashPoint = bytesToInt(digest, offset);
                hashRing.put(hashPoint, node);
            }
        }
    }

    @Override
    public void remove(E node) {

    }

    @Override
    public E get(Object key) {
        byte[] digest = hashAlgorithm.hash(key.toString());
        int hash = bytesToInt(digest, 0);
        SortedMap<Integer, E> tailMap = hashRing.tailMap(hash);
        if (tailMap.isEmpty()) {
            return hashRing.get(hashRing.firstKey());
        }
        return tailMap.get(tailMap.firstKey());
    }

    /**
     * 16 length byte array to int
     *
     * @param b      byte array
     * @param offset offset
     * @return
     */
    private int bytesToInt(byte[] b, int offset) {
        return ByteBuffer.wrap(b, offset, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }
}
