package ru.simonenko.ilya.spring.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTableProduct {
    private final static String createTableQuery = """
            CREATE TABLE IF NOT EXISTS product (
                id BIGSERIAL PRIMARY KEY,
                user_id BIGSERIAL NOT NULL,
                account_number VARCHAR(20) NOT NULL,
                balance DECIMAL(10,2) DEFAULT 0,
                product_type VARCHAR(10),
                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
            )
            """;

    public CreateTableProduct(final DataSource dataSource) {
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
