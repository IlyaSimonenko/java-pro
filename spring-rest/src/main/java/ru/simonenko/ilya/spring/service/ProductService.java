package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simonenko.ilya.spring.dao.ProductDao;
import ru.simonenko.ilya.spring.db.ProductEntity;
import ru.simonenko.ilya.spring.dto.Product;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product createProduct(Product product) {

        ProductEntity productEntity = productDao.createProduct(product);

        return createProduct(productEntity);
    }

    public List<Product> getAllProductByUserId(Long userId) {
        return productDao.getAllProductByUserId(userId).stream()
                .map(this::createProduct)
                .toList();
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id)
                .map(this::createProduct)
                .orElse(null);
    }

    private Product createProduct(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getUserId(),
                entity.getAccountNumber(),
                entity.getBalance(),
                entity.getProductType()
        );
    }

}
