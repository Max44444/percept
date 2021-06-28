package bsa.java.concurrency.fs;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;


@Repository
public class FileSystemRepository implements FileSystem {

    Path rootDirectory;

    @SneakyThrows
    public FileSystemRepository(@Value("${fs-root-directory}") String rootDirectory) {
        this.rootDirectory = Paths.get(rootDirectory);

        if (!Files.exists(this.rootDirectory)) {
            Files.createDirectories(this.rootDirectory);
        }
    }

    @Override
    public CompletableFuture<String> saveFile(byte[] byteImage, String name) {
        return CompletableFuture.supplyAsync(() -> executeSaveToFile(byteImage, name));
    }

    @SneakyThrows
    private String executeSaveToFile(byte[] byteImage, String name) {
        var pathToFile = Path.of(rootDirectory.toString(), name);
        return Files.write(pathToFile, byteImage).toString();
    }

}
