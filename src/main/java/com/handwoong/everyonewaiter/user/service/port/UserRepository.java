package com.handwoong.everyonewaiter.user.service.port;

import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;

public interface UserRepository {

    User save(User user);

    boolean existsByUsername(Username username);
}
