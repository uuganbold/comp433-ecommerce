package edu.luc.comp433.exceptions;

public class InvalidFormatException extends Exception {

    public InvalidFormatException() {
    }

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
