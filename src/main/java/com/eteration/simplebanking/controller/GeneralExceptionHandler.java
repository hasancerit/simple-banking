package com.eteration.simplebanking.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        final List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath().toString() + ": " + violation.getMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        final List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }
}
