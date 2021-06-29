package bsa.java.concurrency.fs;

import bsa.java.concurrency.exeption.DeleteFilesException;
import bsa.java.concurrency.exeption.GettingImageException;
import bsa.java.concurrency.exeption.SavingFileException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
public class FileSystemService {

    @Autowired
    private FileSystem repository;

    public String saveFile(byte[] file, String filename) {
        try {
            return repository.saveFile(file, filename);
        } catch (IOException e) {
            throw new SavingFileException(e);
        }
    }

    public void deleteFileByName(String filename) {
        try {
            repository.deleteFileByName(filename);
        } catch (IOException e) {
            throw new DeleteFilesException(e);
        }
    }

    public void deleteAll() {
        try {
            repository.deleteAll();
        } catch (IOException e) {
            throw new DeleteFilesException(e);
        }
    }

    public InputStream getImageByPathAsInputStream(String path) {
        try {
            return repository.getImageByPathAsInputStream(path);
        } catch (IOException e) {
            throw new GettingImageException(e);
        }
    }
}
