package bsa.java.concurrency.fs;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


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

    @Override
    public void deleteFileByName(String name) throws IOException {
        var pathToFile = Path.of(rootDirectory.toString(), name);
        Files.deleteIfExists(pathToFile);
    }

    @SneakyThrows
    @Override
    public void deleteAll() {
        try (var files = Files.newDirectoryStream(rootDirectory)) {
            for (var path : files) {
                Files.delete(path);
            }
        }
    }

    @SneakyThrows
    private String executeSaveToFile(byte[] byteImage, String name) {
        var pathToFile = Path.of(rootDirectory.toString(), name);
        return Files.write(pathToFile, byteImage).toString();
    }

}
