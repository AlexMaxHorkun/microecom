package com.microecom.customerservice.http.controller;

import com.microecom.customerservice.http.controller.data.ExceptionRead;
import com.microecom.customerservice.http.controller.data.InvalidInputExceptionRead;
import com.microecom.customerservice.model.exception.InvalidInputDataException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidInputDataException.class})
    protected final ResponseEntity<Object> handleInvalidInputException(InvalidInputDataException ex, WebRequest webRequest) {
        return handleExceptionInternal(
                ex,
                new InvalidInputExceptionRead(ex),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest
        );
    }

    @ExceptionHandler(value = {Exception.class})
    protected final ResponseEntity<Object> handleUnexpectedExceptions(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, new ExceptionRead("Internal error occurred, please try again later"),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return super.handleExceptionInternal(ex, new InvalidInputExceptionRead(ex), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }
}
