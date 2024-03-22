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

    @Autowired
    public CoinsController(final CoinsService coinsService) {
        this.coinsService = coinsService;
    }

    @PostMapping("/execute")
    public ExecuteCoinsResponse execute(@RequestBody ExecuteCoinsRequest request) {
        return coinsService.execute(request);
    }

}
