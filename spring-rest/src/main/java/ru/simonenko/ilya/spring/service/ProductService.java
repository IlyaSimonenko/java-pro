package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.simonenko.ilya.spring.dao.ProductDao;
import ru.simonenko.ilya.spring.dto.Product;

import java.util.List;

public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product createProduct(Product product) {
        return productDao.createProduct(product);
    }

    public List<Product> getAllProductByUserId(Long userId) {
        return productDao.getAllProductByUserId(userId);
    }

    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

}
