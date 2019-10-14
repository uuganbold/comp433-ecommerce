package edu.luc.comp433.exceptions;

public class InformationNotSufficientException extends RuntimeException {

    public InformationNotSufficientException() {
    }

    public InformationNotSufficientException(String message) {
        super(message);
    }

    public InformationNotSufficientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformationNotSufficientException(Throwable cause) {
        super(cause);
    }

    public InformationNotSufficientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
