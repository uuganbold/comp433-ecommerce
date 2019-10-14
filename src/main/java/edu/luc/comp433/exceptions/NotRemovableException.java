package edu.luc.comp433.exceptions;

public class NotRemovableException extends RuntimeException {

    public NotRemovableException() {
    }

    public NotRemovableException(String message) {
        super(message);
    }

    public NotRemovableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRemovableException(Throwable cause) {
        super(cause);
    }

    public NotRemovableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
