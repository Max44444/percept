package bsa.java.concurrency.image;

import bsa.java.concurrency.fs.FileSystemService;
import bsa.java.concurrency.image.dto.SearchResultDTO;
import bsa.java.concurrency.image.model.Image;
import bsa.java.concurrency.util.hasher.Hasher;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class ImageService {

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    Hasher hasher;

    public void uploadImages(MultipartFile[] files) {
        Arrays.stream(files).forEach(this::uploadImage);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile file) {
        var byteImage = file.getBytes();
        var id = UUID.randomUUID();
        var filename = id + ".png";
        var currentUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        var path = fileSystemService.saveFile(byteImage, filename);
        log.error(path);

        var image = Image.builder()
                .id(id)
                .hash(hasher.calculateHash(byteImage))
                .url(currentUrl + "/files/" + filename)
                .build();

        imageRepository.save(image);
    }

    @SneakyThrows
    public List<SearchResultDTO> searchMatches(MultipartFile file, double threshold) {
        var byteImage = file.getBytes();
        var hash = hasher.calculateHash(byteImage);
        log.error(hash);
        var images = imageRepository.matchSimilarImagesByHash(hash, threshold);

        if (images.isEmpty()) {
            uploadImage(file);
        }

        return images;
    }

    void deleteImageById(UUID id) {
        imageRepository.deleteByImageId(id);
        fileSystemService.deleteFileByName(id.toString() + ".png");
    }

    public void deleteAllImages() {
        imageRepository.deleteAll();
        fileSystemService.deleteAll();
    }
}
