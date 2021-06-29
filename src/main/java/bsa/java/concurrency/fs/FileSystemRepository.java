package bsa.java.concurrency.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileSystemRepository implements FileSystem {

    @Autowired
    @Qualifier("fsRepository")
    private Path rootDirectory;

    @Override
    public String saveFile(byte[] byteImage, String name) throws IOException {
        var pathToFile = Path.of(rootDirectory.toString(), name);
        return Files.write(pathToFile, byteImage).toString();
    }

    @Override
    public void deleteFileByName(String name) throws IOException {
        var pathToFile = Path.of(rootDirectory.toString(), name);
        Files.deleteIfExists(pathToFile);
    }

    @Override
    public void deleteAll() throws IOException {
        try (var files = Files.newDirectoryStream(rootDirectory)) {
            for (var path : files) {
                Files.delete(path);
            }
        }
    }

    @Override
    public InputStream getImageByPathAsInputStream(String path) throws IOException {
        return Files.newInputStream(Path.of(rootDirectory.toString(), path));
    }

}
