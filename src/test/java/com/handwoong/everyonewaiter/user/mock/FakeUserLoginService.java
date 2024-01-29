package com.handwoong.everyonewaiter.user.mock;

import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.dto.UserLogin;
import com.handwoong.everyonewaiter.user.service.port.UserLoginService;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;

public class FakeUserLoginService implements UserLoginService {

    private final UserRepository userRepository;

    public FakeUserLoginService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JwtToken login(final UserLogin userLogin) {
        final User user = userRepository.findByUsername(userLogin.username())
            .orElseThrow(() -> new BadCredentialsException("자격 증명에 실패하였습니다."));
        final boolean passwordMatch = user.getPassword().equals(userLogin.password());
        if (!passwordMatch) {
            throw new BadCredentialsException("자격 증명에 실패하였습니다.");
        }
        return new JwtToken("accessToken");
    }
}
