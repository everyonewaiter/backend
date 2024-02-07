package com.handwoong.everyonewaiter.util;

import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.store.application.StoreServiceImpl;
import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.controller.StoreController;
import com.handwoong.everyonewaiter.store.controller.port.StoreService;
import com.handwoong.everyonewaiter.store.mock.FakeStoreRepository;
import com.handwoong.everyonewaiter.user.application.UserServiceImpl;
import com.handwoong.everyonewaiter.user.application.port.UserLoginService;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.controller.UserController;
import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.mock.FakeUserLoginService;
import com.handwoong.everyonewaiter.user.mock.FakeUserRepository;
import com.handwoong.everyonewaiter.waiting.domain.WaitingValidator;
import java.time.LocalDateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestContainer {

    public final PasswordEncoder passwordEncoder;
    public final FakeTimeHolder timeHolder;

    public final UserRepository userRepository;
    public final UserLoginService userLoginService;
    public final UserService userService;
    public final UserController userController;

    public final StoreRepository storeRepository;
    public final StoreService storeService;
    public final StoreController storeController;

    public final WaitingValidator waitingValidator;

    public TestContainer() {
        this.passwordEncoder = new FakePasswordEncoder("encode");
        this.timeHolder = new FakeTimeHolder();

        this.userRepository = new FakeUserRepository();
        this.userLoginService = new FakeUserLoginService(userRepository);
        this.userService = new UserServiceImpl(userRepository, userLoginService, passwordEncoder, timeHolder);
        this.userController = new UserController(userService);

        this.storeRepository = new FakeStoreRepository();
        this.storeService = new StoreServiceImpl(userRepository, storeRepository);
        this.storeController = new StoreController(storeService);

        this.waitingValidator = new WaitingValidator(userRepository, storeRepository, timeHolder);
    }

    public void setSecurityContextAuthentication(final Username username) {
        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, "");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void setTimeHolder(final LocalDateTime fixedTime) {
        this.timeHolder.setMillis(fixedTime);
    }
}
