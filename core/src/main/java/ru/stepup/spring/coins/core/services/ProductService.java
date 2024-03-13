package ru.stepup.spring.coins.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;
import ru.stepup.spring.coins.core.exceptions.ResourceDoesNotMeetConditionsException;
import ru.stepup.spring.coins.core.exceptions.ResourceNotFoundException;
import ru.stepup.spring.coins.core.integrations.IProductIntegration;

import java.math.BigDecimal;
import java.util.List;

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

    public ProductResponse getProductById(Long id) {
        return productIntegration.getProductById(id)
                .map(product -> {
                    if (BigDecimal.ZERO.compareTo(product.getBalance()) < 0) {
                        return new ProductResponse(
                                product.getId(),
                                product.getUserId(),
                                product.getAccountNumber(),
                                product.getBalance(),
                                product.getProductType()
                        );
                    }
                    throw new ResourceDoesNotMeetConditionsException("Не достаточно средств у продукта");
                })
                .orElseThrow(() -> new ResourceNotFoundException("Запрашиваемого продукта с id: " + id + " не существует"));
    }

}
