package bsa.java.concurrency.exeption;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class Handler {

    @ExceptionHandler(CannotCalculateCashException.class)
    public ResponseEntity<Object> handleCannotCalculateCashException(CannotCalculateCashException exception) {
        log.error(exception);
        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(DeleteFilesException.class)
    public ResponseEntity<Object> handleDeleteFilesException(DeleteFilesException exception) {
        log.error(exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(GettingImageException.class)
    public ResponseEntity<Object> handleGettingImageException(GettingImageException exception) {
        log.error(exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InitializeFileSystemRepositoryException.class)
    public ResponseEntity<Object> handleInitializeFileSystemRepositoryException(InitializeFileSystemRepositoryException exception) {
        log.error(exception);
        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(SavingFileException.class)
    public ResponseEntity<Object> handleSavingFileException(SavingFileException exception) {
        log.error(exception);
        return ResponseEntity.status(500).build();
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> defaultHandler(Throwable exception) {
        log.error(exception);
        return ResponseEntity.status(500).build();
    }

}
