package bsa.java.concurrency.image;

import bsa.java.concurrency.image.dto.SearchResultDTO;
import bsa.java.concurrency.image.model.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends CrudRepository<Image, UUID> {

    @Query(nativeQuery = true, value =
            "SELECT imageId, matchPercent, imageUrl FROM (" +
                "SELECT CAST(id AS VARCHAR) AS imageId, url AS imageUrl, " +
                "(1.0 - length(replace(CAST(CAST(hash AS BIT(64)) # CAST(:hash AS BIT(64)) AS TEXT), '0', '')) / 64.0) as matchPercent " +
                "FROM images" +
            ") data " +
            "WHERE data.matchPercent >= :minMatchPercent")
    List<SearchResultDTO> matchSimilarImagesByHash(Long hash, Double minMatchPercent);

}
