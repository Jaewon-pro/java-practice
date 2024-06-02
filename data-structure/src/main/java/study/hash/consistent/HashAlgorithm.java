package study.hash.consistent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

public enum HashAlgorithm {
    MD5(bytes -> {
        try {
            return MessageDigest.getInstance("MD5").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }),
    ;

    private final Function<byte[], byte[]> hashFunction;

    HashAlgorithm(Function<byte[], byte[]> hashFunction) {
        this.hashFunction = hashFunction;
    }

    public byte[] hash(byte[] bytes) {
        return hashFunction.apply(bytes);
    }

    public byte[] hash(String str) {
        return hash(str.getBytes());
    }

}
