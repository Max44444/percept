package bsa.java.concurrency.exeption;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class InitializeFileSystemRepositoryException extends Exception {

    public InitializeFileSystemRepositoryException(Throwable cause) {
        super(cause);
    }
}
