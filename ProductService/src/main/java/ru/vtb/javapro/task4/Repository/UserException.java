package ru.vtb.javapro.task4.Repository;


import org.springframework.http.HttpStatus;


public class UserException extends RuntimeException{
    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public UserException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
