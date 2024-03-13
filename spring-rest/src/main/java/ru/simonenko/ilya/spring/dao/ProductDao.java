package ru.simonenko.ilya.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.simonenko.ilya.spring.db.ProductEntity;
import ru.simonenko.ilya.spring.dto.Product;
import ru.simonenko.ilya.spring.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDao(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(Product product) {

        return productRepository.save(new ProductEntity(product.getUserId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType()
        ));

    }

    public List<ProductEntity> getAllProductByUserId(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);

    }

}
