package ru.simonenko.ilya.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.simonenko.ilya.spring.dto.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final DataSource dataSource;
    private final String createUserQuery = "INSERT INTO users (username) VALUES (?)";
    private final String deleteUserQuery = "DELETE FROM users WHERE id = ?";
    private final String findUserByIdQuery = "SELECT * FROM users WHERE id = ?";

    private final String findUsersQuery = "SELECT * FROM users";

    @Autowired
    public UserDao(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createUser(User user) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(createUserQuery)) {

            statement.setString(1, user.getUsername());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(deleteUserQuery)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(findUserByIdQuery)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getLong("id"), resultSet.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(findUsersQuery)) {
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id"), resultSet.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
