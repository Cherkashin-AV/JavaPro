package ru.vtb.javapro.task4.pay.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class IntegrationException extends RuntimeException{
    private final HttpStatus status;

    public IntegrationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
