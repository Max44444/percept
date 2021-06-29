package bsa.java.concurrency.fs;

import java.io.IOException;
import java.io.InputStream;

public interface FileSystem {

    String saveFile(byte[] file, String filename) throws IOException;

    void deleteFileByName(String name) throws IOException;

    void deleteAll() throws IOException;

    InputStream getImageByPathAsInputStream(String path) throws IOException;
}
