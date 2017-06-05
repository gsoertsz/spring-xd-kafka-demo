package org.gsoertsz.xd.kafka.gateway.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gsoertsz on 19/5/17.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessEventException extends Exception {

    public BusinessEventException(String message) {
        super(message);
    }

    public BusinessEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
