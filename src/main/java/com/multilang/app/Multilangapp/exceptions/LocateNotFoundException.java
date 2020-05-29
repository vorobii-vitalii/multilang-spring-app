package com.multilang.app.Multilangapp.exceptions;

public class LocateNotFoundException extends RuntimeException {

    public LocateNotFoundException() {
    }

    public LocateNotFoundException(String message) {
        super(message);
    }

    public LocateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
