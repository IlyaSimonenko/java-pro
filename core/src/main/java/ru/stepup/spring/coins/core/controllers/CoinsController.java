package ru.stepup.spring.coins.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.api.ExecuteCoinsResponse;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;
import ru.stepup.spring.coins.core.services.CoinsService;
import ru.stepup.spring.coins.core.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coins")
public class CoinsController {
    private final CoinsService coinsService;
    private final ProductService productService;

    @Autowired
    public CoinsController(final CoinsService coinsService,
                           final ProductService productService) {
        this.coinsService = coinsService;
        this.productService = productService;
    }

    @PostMapping("/execute")
    public ExecuteCoinsResponse execute(@RequestBody ExecuteCoinsRequest request) {
        return coinsService.execute(request);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/products/user/{userId}")
    public ResponseEntity<List<ProductResponse>> getAllProductByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(productService.getAllProductByUserId(userId));
    }

    @PostMapping("/products/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

}
