package ru.vtb.javapro.task4.pay;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.vtb.javapro.task4.pay.configuration.ClientServiceProperties;
import ru.vtb.javapro.task4.pay.configuration.ExternalServiceErrorHandler;
import ru.vtb.javapro.task4.pay.configuration.ProductServiceProperties;
import ru.vtb.javapro.task4.pay.configuration.RestTemplateProperties;


@Configuration
@EnableConfigurationProperties({ProductServiceProperties.class, ClientServiceProperties.class})
public class PayServiceConfig {

    @Bean
    public RestTemplate productRestClient(ProductServiceProperties productServiceProperties,
        ExternalServiceErrorHandler productErrorHandler) {

        RestTemplateProperties serviceProperties = productServiceProperties.getProductService();
        return new RestTemplateBuilder()
                   .rootUri(serviceProperties.getUrl())
                   .setConnectTimeout(serviceProperties.getConnectTimeout())
                   .setReadTimeout(serviceProperties.getReadTimeout())
                   .errorHandler(productErrorHandler)
                   .build();
    }

    @Bean
    public RestTemplate clientRestClient(ClientServiceProperties clientServiceProperties,
        ExternalServiceErrorHandler productErrorHandler){

        RestTemplateProperties serviceProperties = clientServiceProperties.getProductService();
        return new RestTemplateBuilder()
                   .rootUri(serviceProperties.getUrl())
                   .setConnectTimeout(serviceProperties.getConnectTimeout())
                   .setReadTimeout(serviceProperties.getReadTimeout())
                   .errorHandler(productErrorHandler)
                   .build();
    }
}
