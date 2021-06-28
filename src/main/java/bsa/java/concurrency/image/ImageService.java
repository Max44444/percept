package bsa.java.concurrency.image;

import bsa.java.concurrency.fs.FileSystemService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;

@Log4j2
@Service
public class ImageService {

    @Autowired
    FileSystemService fileSystemService;

    public void uploadImages(MultipartFile[] files) {
        Arrays.stream(files).forEach(this::uploadImage);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile file) {
        var byteImage = file.getBytes();
        var filename = UUID.randomUUID().toString() ;

        var pathToFile = fileSystemService.saveFile(byteImage, filename);
        log.error(pathToFile);
    }

}
