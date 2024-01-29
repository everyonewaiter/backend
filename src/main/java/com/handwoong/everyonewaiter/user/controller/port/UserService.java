package com.handwoong.everyonewaiter.user.controller.port;

import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import com.handwoong.everyonewaiter.user.dto.UserLogin;

public interface UserService {

    Long join(UserJoin userJoin);

    JwtToken login(UserLogin userLogin);
}
