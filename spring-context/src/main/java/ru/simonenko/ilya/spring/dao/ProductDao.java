package ru.simonenko.ilya.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import ru.simonenko.ilya.spring.dto.Product;
import ru.simonenko.ilya.spring.enums.ProductTypeEnum;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private final DataSource dataSource;
    private final static String createUserQuery = "INSERT INTO product (account_number,balance,product_type,user_id) VALUES (?,?,?,?) RETURNING *";
    private final static String findAllProductByUserId = "SELECT * FROM product WHERE user_id = ?";
    private final static String findProductByIdQuery = "SELECT * FROM product WHERE id = ?";

    @Autowired
    public ProductDao(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Product createProduct(Product product) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(createUserQuery)) {

            statement.setString(1, product.getAccountNumber());
            statement.setBigDecimal(2, product.getBalance());
            statement.setString(3, product.getProductType().getProductType());
            statement.setLong(4, product.getUserId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createProductBasedOnResultSet(resultSet);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getAllProductByUserId(Long userId) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(findAllProductByUserId)) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(createProductBasedOnResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(Long id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(findProductByIdQuery)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createProductBasedOnResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product createProductBasedOnResultSet(ResultSet resultSet) throws SQLException {

        return new Product(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("account_number"),
                resultSet.getBigDecimal("balance"),
                ProductTypeEnum.getEnum(resultSet.getString("product_type"))
        );

    }

}
