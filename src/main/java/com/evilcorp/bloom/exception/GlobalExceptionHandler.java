package com.evilcorp.bloom.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final String psqlFKConstraintCode = "constraint [23503]";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleDtoValidationException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityException(DataIntegrityViolationException e) {
    if (e.getMessage() != null && e.getMessage().contains(psqlFKConstraintCode)) {
      return new ResponseEntity<>(String.format("Foreign key not found: %s", e.getMessage()), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception e) {
    return new ResponseEntity<>("an unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
