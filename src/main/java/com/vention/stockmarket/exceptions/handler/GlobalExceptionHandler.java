package com.vention.stockmarket.exceptions.handler;

import com.vention.stockmarket.exceptions.CustomResourceAlreadyExistException;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.exceptions.CustomUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // user input validation exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundExceptionClass(CustomResourceNotFoundException exception) {
        String resBody = "Not found";
        if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
            resBody = exception.getMessage();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resBody);
    }

    @ExceptionHandler(CustomResourceAlreadyExistException.class)
    public ResponseEntity<String> handleResourceAlreadyExistException(CustomResourceAlreadyExistException exception) {
        String resBody = "Resource already exist";
        if (exception.getMessage() != null && !exception.getMessage().isEmpty()) {
            resBody = exception.getMessage();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resBody);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedExceptions() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptionClass(Exception exception) {
        log.info("Unexpected : " + exception);
        String resBody;
        resBody = exception.getMessage() != null ? exception.getMessage() : "Unexpected error, try again later!";
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resBody);
    }
}