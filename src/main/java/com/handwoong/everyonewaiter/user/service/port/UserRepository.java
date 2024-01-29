package com.handwoong.everyonewaiter.user.service.port;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean existsByUsername(Username username);

    Optional<User> findByUsername(Username username);

    User findByUsernameOrElseThrow(Username username);
}
