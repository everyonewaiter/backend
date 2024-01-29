package com.handwoong.everyonewaiter.user.controller.port;

import com.handwoong.everyonewaiter.user.dto.UserJoin;

public interface UserService {

    Long join(UserJoin userJoin);
}
