package ru.vtb.javapro.task4.dao;


import org.springframework.http.HttpStatus;


public class ProductException extends RuntimeException{

    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public ProductException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
