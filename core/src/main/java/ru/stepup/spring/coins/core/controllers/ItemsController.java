package ru.stepup.spring.coins.core.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.stepup.spring.coins.core.dtos.ItemDto;
import ru.stepup.spring.coins.core.dtos.PageDto;
import ru.stepup.spring.coins.core.exceptions.ResourceNotFoundException;
import ru.stepup.spring.coins.core.services.ItemsService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private static final Logger logger = LoggerFactory.getLogger(ItemsController.class.getName());

    private final ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable Long id) {
        return itemsService.findById(id).map(item -> new ItemDto(item.getId(), item.getTitle())).orElseThrow(() -> new ResourceNotFoundException("Не найден предмет с id: " + id));
    }

    @GetMapping
    public PageDto<ItemDto> findAll() {
        return new PageDto<>(itemsService.findAll().stream().map(i -> new ItemDto(i.getId(), i.getTitle())).collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ItemDto itemDto) {
        logger.info("created: {}", itemDto.toString());
    }

    @PutMapping
    public void modify(@RequestBody ItemDto itemDto) {
        logger.info("modify: {}", itemDto.toString());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        itemsService.deleteById(id);
    }
}
