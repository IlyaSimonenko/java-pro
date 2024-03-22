package ru.stepup.spring.coins.core.integrations;

import ru.simonenko.ilya.spring.dto.Product;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface IProductIntegration {
    ProductResponse createProduct(ProductRequest product);

    List<ProductResponse> getAllProductByUserId(Long userId);

    Optional<Product> getProductById(Long id);
}
