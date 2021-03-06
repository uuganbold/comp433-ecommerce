package edu.luc.comp433.exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException() {
    }

    public EntryNotFoundException(String message) {
        super(message);
    }

    public EntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
