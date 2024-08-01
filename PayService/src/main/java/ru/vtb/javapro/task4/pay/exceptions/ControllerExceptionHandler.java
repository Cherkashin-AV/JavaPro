package ru.vtb.javapro.task4.pay.exceptions;


import java.net.ConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vtb.javapro.task4.dto.ResponseErrorDto;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleItemException(IllegalArgumentException exception) {
        return new ResponseErrorDto(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(IntegrationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleItemException(IntegrationException exception) {
        return new ResponseErrorDto(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseErrorDto handleItemException(ConnectException exception) {
        return new ResponseErrorDto(HttpStatus.SERVICE_UNAVAILABLE, "Платежный сервис временно недоступен. Повторите попытку позже.");
    }

}
