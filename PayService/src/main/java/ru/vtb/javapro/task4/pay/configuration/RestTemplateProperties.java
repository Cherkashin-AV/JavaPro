package ru.vtb.javapro.task4.pay.configuration;


import java.time.Duration;
import org.springframework.boot.context.properties.bind.ConstructorBinding;


public class RestTemplateProperties {
    private String url;
    private Duration connectTimeout;
    private Duration readTimeout;

    @ConstructorBinding
    public RestTemplateProperties(String url, Duration connectTimeout, Duration readTimeout) {
        this.url = url;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public String getUrl() {
        return url;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }
}
