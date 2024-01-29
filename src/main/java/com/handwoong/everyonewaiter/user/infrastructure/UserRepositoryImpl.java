package com.handwoong.everyonewaiter.user.infrastructure;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import java.util.Optional;
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

    @Override
    public Optional<User> findByUsername(final Username username) {
        return userJpaRepository.findByUsername(username.toString()).map(UserEntity::toModel);
    }

    @Override
    public User findByUsernameOrElseThrow(final Username username) {
        return findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다.", username.toString()));
    }
}
