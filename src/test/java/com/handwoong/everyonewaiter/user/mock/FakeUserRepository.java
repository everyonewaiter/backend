package com.handwoong.everyonewaiter.user.mock;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public User save(final User user) {
        final Long id = Objects.nonNull(user.getId()) ? user.getId().value() : sequence++;
        final User newUser = create(id, user);
        database.put(id, newUser);
        return newUser;
    }

    @Override
    public boolean existsByUsername(final Username username) {
        return !database.values()
            .stream()
            .filter(user -> user.getUsername().equals(username))
            .toList()
            .isEmpty();
    }

    @Override
    public Optional<User> findByUsername(final Username username) {
        return database.values()
            .stream()
            .filter(user -> user.getUsername().equals(username))
            .findAny();
    }

    @Override
    public User findByUsernameOrElseThrow(final Username username) {
        return database.values()
            .stream()
            .filter(user -> user.getUsername().equals(username))
            .findAny()
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다.", username.toString()));
    }

    private User create(final Long id, final User user) {
        return User.builder()
            .id(new UserId(id))
            .username(user.getUsername())
            .password(user.getPassword())
            .phoneNumber(user.getPhoneNumber())
            .role(user.getRole())
            .status(user.getStatus())
            .build();
    }
}
