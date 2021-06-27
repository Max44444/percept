package bsa.java.concurrency.fs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class FileSystemRepository implements FileSystem {

    @Value("${fs-root-directory}")
    String rootDirectory;

    @Override
    public CompletableFuture<String> saveFile(byte[] byteImage, String filename) {
        return null;
    }

}
