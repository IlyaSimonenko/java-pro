package ru.simonenko.ilya.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.simonenko.ilya.spring.config.ApplicationConfig;
import ru.simonenko.ilya.spring.dto.User;
import ru.simonenko.ilya.spring.service.UserService;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UserService userService = context.getBean(UserService.class);

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

        userService.deleteUser(id);

        users = userService.getAllUsers();

        userSize = !users.isEmpty() ? users.size() : 0;

        System.out.println("Конечное количество пользователей: " + userSize);

    }

}
