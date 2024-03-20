package ru.simonenko.ilya.spring.repository;

import org.springframework.stereotype.Repository;
import ru.simonenko.ilya.spring.db.UserEntity;
import ru.simonenko.ilya.spring.repository.base.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
}
