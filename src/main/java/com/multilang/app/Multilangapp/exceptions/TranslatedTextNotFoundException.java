package com.multilang.app.Multilangapp.exceptions;

public class TranslatedTextNotFoundException extends RuntimeException {

    public TranslatedTextNotFoundException() {
    }

    public TranslatedTextNotFoundException(String message) {
        super(message);
    }

    public TranslatedTextNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
