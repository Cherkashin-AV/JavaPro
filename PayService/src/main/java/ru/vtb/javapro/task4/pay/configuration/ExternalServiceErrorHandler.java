package ru.vtb.javapro.task4.pay.configuration;


import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.vtb.javapro.task4.pay.exceptions.IntegrationException;


@Component
public class ExternalServiceErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() ||
                   response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new IntegrationException("Ошибка внешнего сервера:" + response.getBody(), HttpStatus.BAD_REQUEST);
    }
}
