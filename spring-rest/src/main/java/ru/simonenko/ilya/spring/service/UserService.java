package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simonenko.ilya.spring.db.UserEntity;
import ru.simonenko.ilya.spring.dto.User;
import ru.simonenko.ilya.spring.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(String username) {
        User user = new User(null, username);
        userRepository.save(new UserEntity(user.getUsername()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::createProduct)
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
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
