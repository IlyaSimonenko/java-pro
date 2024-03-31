package ru.simonenko.ilya.spring.repository;

import org.springframework.stereotype.Repository;
import ru.simonenko.ilya.spring.db.UserLimitEntity;
import ru.simonenko.ilya.spring.repository.base.BaseRepository;

@Repository
public interface UserLimitRepository extends BaseRepository<UserLimitEntity, Long> {
}
