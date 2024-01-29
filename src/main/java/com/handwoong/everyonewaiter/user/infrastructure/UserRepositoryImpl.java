package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
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

    @Override
    public boolean existsByUsername(final Username username) {
        return userJpaRepository.existsByUsername(username.toString());
    }
}
