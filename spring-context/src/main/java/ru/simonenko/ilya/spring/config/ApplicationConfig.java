package ru.simonenko.ilya.spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.yaml.snakeyaml.Yaml;
import ru.simonenko.ilya.spring.dao.ProductDao;
import ru.simonenko.ilya.spring.dao.UserDao;
import ru.simonenko.ilya.spring.db.CreateTableProduct;
import ru.simonenko.ilya.spring.db.CreateTableUsers;
import ru.simonenko.ilya.spring.service.ProductService;
import ru.simonenko.ilya.spring.service.UserService;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    private final String url;
    private final String username;
    private final String password;
    private final String driverClassName;

    public ApplicationConfig() {
        Map<String, String> datasourceValues = creatingMapBasedOnDatabase();

        url = datasourceValues.get("url");
        username = datasourceValues.get("username");
        password = datasourceValues.get("password");
        driverClassName = datasourceValues.get("driver-class-name");
    }

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

    private Map<String, String> creatingMapBasedOnDatabase() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yaml");

        Map<String, Map<String, Map<String, String>>> yamlValues = yaml.load(inputStream);

        return yamlValues.get("spring").get("datasource");
    }

}
