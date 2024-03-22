package ru.stepup.spring.coins.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;
import ru.stepup.spring.coins.core.integrations.IProductIntegration;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final IProductIntegration productIntegration;

    @Autowired
    public ProductService(final IProductIntegration productIntegration) {
        this.productIntegration = productIntegration;
    }

    public ProductResponse createProduct(ProductRequest product) {
        return productIntegration.createProduct(product);
    }

    public List<ProductResponse> getAllProductByUserId(Long userId) {
        return productIntegration.getAllProductByUserId(userId);
    }

    public Optional<ProductResponse> getProductById(Long id) {
        return productIntegration.getProductById(id)
                .map(product -> new ProductResponse(
                                product.getId(),
                                product.getUserId(),
                                product.getAccountNumber(),
                                product.getBalance(),
                                product.getProductType()
                        )
                );
    }

}
