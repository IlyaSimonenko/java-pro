package ru.stepup.spring.coins.core.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.simonenko.ilya.spring.dto.Product;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductIntegration implements IProductIntegration {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductIntegration(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponse createProduct(ProductRequest product) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<ProductRequest> request = new HttpEntity<>(
                product,
                httpHeaders
        );

        Product response = restTemplate.postForObject("/create", request, Product.class);

        return new ProductResponse(
                response.getId(),
                response.getUserId(),
                response.getAccountNumber(),
                response.getBalance(),
                response.getProductType()
        );
    }

    @Override
    public List<ProductResponse> getAllProductByUserId(Long userId) {

        List<Product> products = restTemplate.exchange("/user/{userId}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        }, userId).getBody();

        if (products != null && !products.isEmpty()) {
            return products.stream()
                    .map(r -> new ProductResponse(
                            r.getId(),
                            r.getUserId(),
                            r.getAccountNumber(),
                            r.getBalance(),
                            r.getProductType()
                    ))
                    .toList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<Product> getProductById(Long id) {

        return Optional.ofNullable(restTemplate.getForObject("/{id}", Product.class, id));

    }

}
