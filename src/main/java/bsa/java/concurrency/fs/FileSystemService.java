package bsa.java.concurrency.fs;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileSystemService {

    @Autowired
    FileSystem repository;

    @SneakyThrows
    public String saveFile(byte[] file, String filename) {
        return repository.saveFile(file, filename).get();
    }

    @SneakyThrows
    public void deleteFileByName(String filename) {
        repository.deleteFileByName(filename);
    }
}
