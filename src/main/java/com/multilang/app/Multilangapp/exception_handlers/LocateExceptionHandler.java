package com.multilang.app.Multilangapp.exception_handlers;

import com.multilang.app.Multilangapp.exceptions.LocateAlreadyExistsException;
import com.multilang.app.Multilangapp.exceptions.LocateNotFoundException;
import com.multilang.app.Multilangapp.helpers.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LocateExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse<String>> handleLocateNotFound(LocateNotFoundException e) {
        ErrorResponse<String> response = new ErrorResponse<>(
                "Locate was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse<String>> handleLocateAlreadyExists(LocateAlreadyExistsException e) {
        ErrorResponse<String> response = new ErrorResponse<>(
                "Locate already exists",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
