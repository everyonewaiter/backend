package com.handwoong.everyonewaiter.user.service.port;

import com.handwoong.everyonewaiter.user.domain.User;

public interface UserRepository {

    User save(User user);
}
