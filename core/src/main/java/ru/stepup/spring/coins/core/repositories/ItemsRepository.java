package ru.stepup.spring.coins.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepup.spring.coins.core.entities.Item;

public interface ItemsRepository extends JpaRepository<Item, Long> {
}