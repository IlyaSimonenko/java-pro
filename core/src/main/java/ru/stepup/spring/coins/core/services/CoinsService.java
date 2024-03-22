package ru.stepup.spring.coins.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.api.ExecuteCoinsRequest;
import ru.stepup.spring.coins.core.api.ExecuteCoinsResponse;
import ru.stepup.spring.coins.core.api.ProductResponse;
import ru.stepup.spring.coins.core.configurations.properties.CoreProperties;
import ru.stepup.spring.coins.core.exceptions.BadRequestException;
import ru.stepup.spring.coins.core.exceptions.ResourceDoesNotMeetConditionsException;
import ru.stepup.spring.coins.core.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CoinsService {
    private final CoreProperties coreProperties;
    private final ExecutorService executorService;
    private final ProductService productService;

    @Autowired
    public CoinsService(final CoreProperties coreProperties,
                        final ExecutorService executorService,
                        final ProductService productService) {
        this.coreProperties = coreProperties;
        this.executorService = executorService;
        this.productService = productService;
    }

    public ExecuteCoinsResponse execute(ExecuteCoinsRequest request) {
        if (coreProperties.getNumbersBlockingEnabled()) {
            if (coreProperties.getBlockedNumbers().contains(request.number())) {
                throw new BadRequestException("Указан заблокированный номер кошелька", "BLOCKED_ACCOUNT_NUMBER");
            }
        }
        Optional<ProductResponse> productResponse = productService.getProductById(Long.valueOf(request.productId()));

        productResponse.ifPresentOrElse(element -> {
                    if (BigDecimal.ZERO.compareTo(element.balance()) >= 0) {
                        throw new ResourceDoesNotMeetConditionsException("Не достаточно средств у продукта: " + element);
                    }
                },
                () -> new ResourceNotFoundException("Запрашиваемого продукта с id: " + request.productId() + " не существует")
        );

        ExecuteCoinsResponse response = executorService.execute(request);
        return response;
    }
}
