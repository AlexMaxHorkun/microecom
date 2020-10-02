package com.microecom.customerservice.http.controller.data;

public class ExceptionRead {
    private final String message;

    public ExceptionRead(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }
}
