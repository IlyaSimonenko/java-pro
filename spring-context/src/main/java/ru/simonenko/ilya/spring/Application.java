package ru.simonenko.ilya.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.simonenko.ilya.spring.config.ApplicationConfig;
import ru.simonenko.ilya.spring.dto.Product;
import ru.simonenko.ilya.spring.dto.User;
import ru.simonenko.ilya.spring.enums.ProductTypeEnum;
import ru.simonenko.ilya.spring.service.ProductService;
import ru.simonenko.ilya.spring.service.UserService;

import java.math.BigDecimal;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = context.getBean(UserService.class);
        ProductService productService = context.getBean(ProductService.class);

        List<User> users = userService.getAllUsers();

        int userSize = !users.isEmpty() ? users.size() : 0;

        System.out.println("Первоначальное количество пользователей: " + userSize);

        userService.createUser("Ilya");

        Long id = userService.getAllUsers().stream()
                .findFirst()
                .map(User::getId)
                .orElse(null);

        User user = userService.getUserById(id);

        System.out.println(user);

        List<Product> products = productService.getAllProductByUserId(user.getId());

        System.out.println("Количество продуктов принадлежащих пользователю по id: " + user.getId() + " равняется " + products.size());

        Product product = new Product(user.getId(), "345", BigDecimal.ONE, ProductTypeEnum.ACCOUNT);

        product = productService.createProduct(product);

        products = productService.getAllProductByUserId(user.getId());

        System.out.println("Количество продуктов принадлежащих пользователю по id: " + user.getId() + " равняется " + products.size());

        product = productService.getProductById(product.getId());

        System.out.println("Полученный продукт: " + product);

        products = productService.getAllProductByUserId(id);

        System.out.println("Количество продуктов по конкретному userId= " + id + ", количество продуктов= " + products.size());

        userService.deleteUser(id);

        users = userService.getAllUsers();

        userSize = !users.isEmpty() ? users.size() : 0;

        System.out.println("Конечное количество пользователей: " + userSize);

    }

}
