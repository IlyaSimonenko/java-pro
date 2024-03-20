package ru.simonenko.ilya.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.simonenko.ilya.spring.db.ProductEntity;
import ru.simonenko.ilya.spring.dto.Product;
import ru.simonenko.ilya.spring.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {

        ProductEntity productEntity = productRepository.save(new ProductEntity(
                product.getUserId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType()
        ));

        return createProduct(productEntity);
    }

    public List<Product> getAllProductByUserId(Long userId) {
        return productRepository.findAllByUserId(userId).stream()
                .map(this::createProduct)
                .toList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
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
