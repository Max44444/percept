package bsa.java.concurrency.image;

import bsa.java.concurrency.fs.FileSystemService;
import bsa.java.concurrency.image.dto.SearchResultDTO;
import bsa.java.concurrency.image.model.Image;
import bsa.java.concurrency.util.hasher.Hasher;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class ImageService {

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Hasher hasher;

    @Value("${application.hostname}")
    private String currentUrl;

    @Value("${application.saving-img-extension}")
    private String imgExtension;

    @Async
    @SneakyThrows
    void saveImage(byte[] byteImage) {
        var id = UUID.randomUUID();
        var futurePath = saveToFile(byteImage, id + imgExtension);
        var futureHash = calculateHash(byteImage);

        saveImageToDB(id, futurePath.get(), futureHash.get());
    }

    @SneakyThrows
    public List<SearchResultDTO> searchMatches(MultipartFile file, double threshold) {
        var byteImage = file.getBytes();
        var hash = hasher.calculateHash(byteImage);
        var images = imageRepository.matchSimilarImagesByHash(hash, threshold);

        if (images.isEmpty()) {
            saveImage(byteImage);
        }

        return images;
    }

    void deleteImageById(UUID id) {
        imageRepository.deleteByImageId(id);
        fileSystemService.deleteFileByName(id.toString() + imgExtension);
    }

    public void deleteAllImages() {
        imageRepository.deleteAll();
        fileSystemService.deleteAll();
    }

    @Async
    CompletableFuture<Long> calculateHash(byte[] file) {
        var hash = hasher.calculateHash(file);
        return CompletableFuture.completedFuture(hash);
    }

    @Async
    CompletableFuture<String> saveToFile(byte[] byteImage, String filename) {
        var path = fileSystemService.saveFile(byteImage, filename);
        return CompletableFuture.completedFuture(path);
    }

    @SneakyThrows
    private void saveImageToDB(UUID id, String path, Long hash) {
        var image = Image.builder()
                .id(id)
                .hash(hash)
                .url(currentUrl + path)
                .build();

        imageRepository.save(image);
    }
}
