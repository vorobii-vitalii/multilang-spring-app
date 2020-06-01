package com.multilang.app.Multilangapp.exception_handlers;

import com.multilang.app.Multilangapp.exceptions.TranslatedTextNotFoundException;
import com.multilang.app.Multilangapp.helpers.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TranslatedTextExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse<String>> handleTranslatedTextNotFound(TranslatedTextNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse<String> errorResponse = new ErrorResponse<>(
                     "TranslatedText not found",
                            System.currentTimeMillis() );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
