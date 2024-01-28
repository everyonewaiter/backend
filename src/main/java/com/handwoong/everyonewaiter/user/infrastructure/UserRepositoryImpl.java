package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(final User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }
}
