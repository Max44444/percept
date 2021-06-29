package bsa.java.concurrency.config;

import bsa.java.concurrency.exeption.InitializeFileSystemRepositoryException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log4j2
@Configuration
public class FileSystemRepositoryPathConfiguration {

    @Value("${application.fs-root-directory}")
    private String rootDirectory;

    @Bean("fsRepository")
    public Path createRootDirectory() throws InitializeFileSystemRepositoryException {
        try {
            var pathToRootDirectory = Path.of(rootDirectory);
            if (!Files.exists(pathToRootDirectory)) {
                Files.createDirectories(pathToRootDirectory);
            }
            return pathToRootDirectory;
        } catch (IOException e) {
            log.fatal("Could not create root directory", e);
            throw new InitializeFileSystemRepositoryException(e);
        }
    }

}

