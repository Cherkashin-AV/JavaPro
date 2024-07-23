package ru.vtb.javapro.task4;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.PROPERTIES")
public class ProductServiceConfig {

    private Logger logger = LoggerFactory.getLogger(ProductServiceConfig.class);

    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.schemas}")
    private String schemas;
    @Value("${spring.datasource.url}")
    private String url;

    private DataSource dataSource;

    @Bean
    public DataSource getHikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setSchema(schemas);
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    @DependsOn("getHikariDataSource")
    public Connection connection() throws SQLException {
        return dataSource.getConnection();
    }
}
