package ru.vtb.javapro.task4.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vtb.javapro.task4.Repository.ProductException;
import ru.vtb.javapro.task4.Repository.UserException;
import ru.vtb.javapro.task4.dto.ResponseErrorDto;


@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler({UserException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDto exceptionHandle(UserException ex){
        return new ResponseErrorDto(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler({ProductException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseErrorDto exceptionHandle(ProductException ex){
        return new ResponseErrorDto(ex.getStatus(), ex.getMessage());
    }
}
