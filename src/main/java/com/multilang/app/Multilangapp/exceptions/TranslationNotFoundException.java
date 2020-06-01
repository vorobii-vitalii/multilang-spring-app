package com.multilang.app.Multilangapp.exceptions;

public class TranslationNotFoundException extends RuntimeException {

    public TranslationNotFoundException() {
    }

    public TranslationNotFoundException(String message) {
        super(message);
    }

    public TranslationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
