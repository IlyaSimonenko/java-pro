package ru.simonenko.ilya.spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import ru.simonenko.ilya.spring.dao.ProductDao;
import ru.simonenko.ilya.spring.dao.UserDao;
import ru.simonenko.ilya.spring.db.CreateTableProduct;
import ru.simonenko.ilya.spring.db.CreateTableUsers;
import ru.simonenko.ilya.spring.service.ProductService;
import ru.simonenko.ilya.spring.service.UserService;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Order(1)
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }

    @Bean
    @Order(2)
    public CreateTableUsers createTableUsers(DataSource dataSource) {
        return new CreateTableUsers(dataSource);
    }

    @Bean
    @Order(3)
    public CreateTableProduct createTableProduct(DataSource dataSource) {
        return new CreateTableProduct(dataSource);
    }

    @Bean
    public UserDao userDao(DataSource dataSource) {
        return new UserDao(dataSource);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

    @Bean
    public ProductDao productDao(DataSource dataSource) {
        return new ProductDao(dataSource);
    }

    @Bean
    public ProductService productService(ProductDao productDao) {
        return new ProductService(productDao);
    }

}
