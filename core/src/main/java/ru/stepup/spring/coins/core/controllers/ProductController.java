package ru.stepup.spring.coins.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.spring.coins.core.api.ProductRequest;
import ru.stepup.spring.coins.core.api.ProductResponse;
import ru.stepup.spring.coins.core.exceptions.ResourceNotFoundException;
import ru.stepup.spring.coins.core.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запрашиваемого продукта с id: " + id + " не существует"))
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProductByUserId(@RequestHeader("userId") Long userId) {
        return ResponseEntity.ok(productService.getAllProductByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

}
