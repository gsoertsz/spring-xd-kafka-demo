package org.gsoertsz.xd.kafka.gateway.api.exceptions;

/**
 * Created by gsoertsz on 22/5/17.
 */
public class BusinessEventStorageException extends Exception {

    public BusinessEventStorageException(String message) {
        super(message);
    }

    public BusinessEventStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
