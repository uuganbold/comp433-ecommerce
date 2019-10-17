package edu.luc.comp433.exceptions;

public class QuantityNotSufficientException extends RuntimeException {

    public QuantityNotSufficientException() {
        super();
    }

    public QuantityNotSufficientException(String message) {
        super(message);
    }

    public QuantityNotSufficientException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuantityNotSufficientException(Throwable cause) {
        super(cause);
    }

    protected QuantityNotSufficientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
