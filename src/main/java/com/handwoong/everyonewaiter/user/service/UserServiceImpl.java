package com.handwoong.everyonewaiter.user.service;

import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import com.handwoong.everyonewaiter.user.exception.AlreadyExistsUsernameException;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long join(final UserJoin userJoin) {
        validateUsername(userJoin.username());
        final User user = User.create(userJoin, passwordEncoder);
        final User joinedUser = userRepository.save(user);
        return joinedUser.getId();
    }

    private void validateUsername(final Username username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistsUsernameException("이미 존재하는 사용자 아이디입니다.", username.toString());
        }
    }
}
