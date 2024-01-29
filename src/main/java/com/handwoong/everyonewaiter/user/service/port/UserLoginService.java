package com.handwoong.everyonewaiter.user.service.port;

import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.dto.UserLogin;

public interface UserLoginService {

    JwtToken login(UserLogin userLogin);
}
