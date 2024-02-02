package com.handwoong.everyonewaiter.util;

import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.common.service.port.TimeHolder;
import com.handwoong.everyonewaiter.user.application.UserServiceImpl;
import com.handwoong.everyonewaiter.user.application.port.UserLoginService;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.controller.UserController;
import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.mock.FakeUserLoginService;
import com.handwoong.everyonewaiter.user.mock.FakeUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestContainer {

    public final PasswordEncoder passwordEncoder;
    public final TimeHolder timeHolder;

    public final UserRepository userRepository;
    public final UserLoginService userLoginService;
    public final UserService userService;
    public final UserController userController;

    public TestContainer() {
        this.passwordEncoder = new FakePasswordEncoder("encode");
        this.timeHolder = new FakeTimeHolder(123456789L);

        this.userRepository = new FakeUserRepository();
        this.userLoginService = new FakeUserLoginService(userRepository);
        this.userService = new UserServiceImpl(userRepository, userLoginService, passwordEncoder, timeHolder);
        this.userController = new UserController(userService);
    }
}
