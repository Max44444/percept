package bsa.java.concurrency.util.hasher;

import java.io.IOException;

public interface Hasher {

    long calculateHash(byte[] imageByteArray) throws IOException;

}
