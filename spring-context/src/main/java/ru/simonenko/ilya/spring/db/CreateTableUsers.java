package ru.simonenko.ilya.spring.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTableUsers {

    private final String createTableQuery = "CREATE TABLE IF NOT EXISTS users (id bigserial primary key, username varchar(255) unique)";

    public CreateTableUsers(final DataSource dataSource) {
        createTable(dataSource);
    }

    public void createTable(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableQuery);

        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
        }

    }

}
