package ru.simonenko.ilya.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.simonenko.ilya.spring.db.UserEntity;
import ru.simonenko.ilya.spring.dto.User;
import ru.simonenko.ilya.spring.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDao(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(new UserEntity(user.getUsername()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

}
