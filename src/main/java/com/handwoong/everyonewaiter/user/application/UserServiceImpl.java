package com.handwoong.everyonewaiter.user.application;

import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.common.service.port.TimeHolder;
import com.handwoong.everyonewaiter.user.application.port.UserLoginService;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import com.handwoong.everyonewaiter.user.dto.UserLogin;
import com.handwoong.everyonewaiter.user.exception.AlreadyExistsUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserLoginService userLoginService;
    private final PasswordEncoder passwordEncoder;
    private final TimeHolder timeHolder;

    @Override
    @Transactional
    public UserId join(final UserJoin userJoin) {
        validateUsername(userJoin.username());
        final User user = User.create(userJoin, passwordEncoder);
        final User joinedUser = userRepository.save(user);
        return joinedUser.getId();
    }

    @Override
    @Transactional
    public JwtToken login(final UserLogin userLogin) {
        final JwtToken userToken = userLoginService.login(userLogin);
        final User user = userRepository.findByUsernameOrElseThrow(userLogin.username());
        final User loggedInUser = user.login(timeHolder);
        userRepository.save(loggedInUser);
        return userToken;
    }

    private void validateUsername(final Username username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistsUsernameException("이미 존재하는 사용자 아이디입니다.", username.toString());
        }
    }
}
