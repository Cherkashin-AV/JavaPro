package ru.vtb.javapro.task4.pay.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "integration")
public class ClientServiceProperties {
    private final RestTemplateProperties clientService;

    public ClientServiceProperties(RestTemplateProperties clientService) {
        this.clientService = clientService;
    }

    public RestTemplateProperties getProductService() {
        return clientService;
    }
}
