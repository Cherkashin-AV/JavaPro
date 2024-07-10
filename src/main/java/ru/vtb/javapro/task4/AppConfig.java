package ru.vtb.javapro.task4;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.PROPERTIES")
public class AppConfig {

    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.schemas}")
    private String schemas;
    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public DataSource getHikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setSchema(schemas);
        return new HikariDataSource(config);
    }
}
