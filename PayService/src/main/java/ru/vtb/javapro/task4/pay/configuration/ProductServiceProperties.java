package ru.vtb.javapro.task4.pay.configuration;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "integration")
public class ProductServiceProperties {
    @Getter
    private final RestTemplateProperties productService;

    public ProductServiceProperties(RestTemplateProperties productService) {
        this.productService = productService;
    }

}
