package ru.simonenko.ilya.spring.repository;

import org.springframework.stereotype.Repository;
import ru.simonenko.ilya.spring.db.ProductEntity;
import ru.simonenko.ilya.spring.repository.base.BaseRepository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByUserId(Long userId);

}
