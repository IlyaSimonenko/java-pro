package ru.stepup.spring.coins.core.services;

import org.springframework.stereotype.Service;
import ru.stepup.spring.coins.core.entities.Item;
import ru.stepup.spring.coins.core.repositories.ItemsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Optional<Item> findById(Long id) {
        return itemsRepository.findById(id);
    }

    public List<Item> findAll() {
        return itemsRepository.findAll();
    }

    public void deleteById(Long id) {
        itemsRepository.deleteById(id);
    }
}
