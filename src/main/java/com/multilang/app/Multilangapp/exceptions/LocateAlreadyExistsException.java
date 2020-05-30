package com.multilang.app.Multilangapp.exceptions;

public class LocateAlreadyExistsException extends RuntimeException {

    public LocateAlreadyExistsException() {
    }

    public LocateAlreadyExistsException(String message) {
        super(message);
    }

    public LocateAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
