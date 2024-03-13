package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simonenko.ilya.spring.dao.UserDao;
import ru.simonenko.ilya.spring.db.UserEntity;
import ru.simonenko.ilya.spring.dto.User;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(String username) {
        User user = new User(null, username);
        userDao.createUser(user);
    }

    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id)
                .map(this::createProduct)
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers().stream()
                .map(this::createProduct)
                .toList();
    }

    private User createProduct(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername()
        );
    }

}
