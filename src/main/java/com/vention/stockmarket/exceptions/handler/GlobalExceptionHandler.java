package com.vention.stockmarket.exceptions.handler;

import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // user input validation exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // For catching Exception.class
    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundExceptionClass(CustomResourceNotFoundException ex, WebRequest request) {
        String resBody = "Not found";
        if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
            resBody = ex.getMessage();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(resBody);
    }

    // For catching Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptionClass(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        String resBody;
        resBody = ex.getMessage() != null ? ex.getMessage() : "Unexpected error, try again later!";
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(resBody);
    }
}