package com.handwoong.everyonewaiter.user.mock;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeUserRepository implements UserRepository {

    private final Map<Long, User> database = new HashMap<>();

    private Long sequence = 1L;

    @Override
    public User save(final User user) {
        final Long id = Objects.nonNull(user.getId()) ? user.getId() : sequence++;
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

    private User create(final Long id, final User user) {
        return User.builder()
            .id(id)
            .username(user.getUsername())
            .password(user.getPassword())
            .phoneNumber(user.getPhoneNumber())
            .role(user.getRole())
            .status(user.getStatus())
            .build();
    }
}
