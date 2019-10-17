package edu.luc.comp433.exceptions;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException() {
    }

    public InvalidStatusException(String message) {
        super(message);
    }


}
