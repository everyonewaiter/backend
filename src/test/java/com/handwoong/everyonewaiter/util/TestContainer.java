package com.handwoong.everyonewaiter.util;

import com.handwoong.everyonewaiter.category.application.CategoryServiceImpl;
import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.controller.CategoryController;
import com.handwoong.everyonewaiter.category.controller.port.CategoryService;
import com.handwoong.everyonewaiter.category.domain.CategoryValidator;
import com.handwoong.everyonewaiter.category.mock.FakeCategoryRepository;
import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.common.mock.FakeUuidHolder;
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
import com.handwoong.everyonewaiter.waiting.application.WaitingServiceImpl;
import com.handwoong.everyonewaiter.waiting.application.port.WaitingRepository;
import com.handwoong.everyonewaiter.waiting.controller.WaitingController;
import com.handwoong.everyonewaiter.waiting.controller.port.WaitingService;
import com.handwoong.everyonewaiter.waiting.domain.WaitingGenerator;
import com.handwoong.everyonewaiter.waiting.domain.WaitingValidator;
import com.handwoong.everyonewaiter.waiting.mock.FakeWaitingRepository;
import java.time.LocalDateTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestContainer {

    public final PasswordEncoder passwordEncoder;
    public final FakeTimeHolder timeHolder;
    public final FakeUuidHolder uuidHolder;

    public final UserRepository userRepository;
    public final UserLoginService userLoginService;
    public final UserService userService;
    public final UserController userController;

    public final StoreRepository storeRepository;
    public final StoreService storeService;
    public final StoreController storeController;

    public final WaitingRepository waitingRepository;
    public final WaitingValidator waitingValidator;
    public final WaitingGenerator waitingGenerator;
    public final WaitingService waitingService;
    public final WaitingController waitingController;

    public final CategoryRepository categoryRepository;
    public final CategoryValidator categoryValidator;
    public final CategoryService categoryService;
    public final CategoryController categoryController;

    public TestContainer() {
        this.passwordEncoder = new FakePasswordEncoder("encode");
        this.timeHolder = new FakeTimeHolder();
        this.uuidHolder = new FakeUuidHolder();

        this.userRepository = new FakeUserRepository();
        this.userLoginService = new FakeUserLoginService(userRepository);
        this.userService = new UserServiceImpl(userRepository, userLoginService, passwordEncoder, timeHolder);
        this.userController = new UserController(userService);

        this.storeRepository = new FakeStoreRepository();
        this.storeService = new StoreServiceImpl(userRepository, storeRepository);
        this.storeController = new StoreController(storeService);

        this.waitingRepository = new FakeWaitingRepository();
        this.waitingValidator = new WaitingValidator(userRepository, storeRepository, timeHolder);
        this.waitingGenerator = new WaitingGenerator(userRepository, storeRepository, waitingRepository);
        this.waitingService = new WaitingServiceImpl(waitingRepository, waitingValidator, waitingGenerator, uuidHolder);
        this.waitingController = new WaitingController(waitingService);

        this.categoryRepository = new FakeCategoryRepository();
        this.categoryValidator = new CategoryValidator(userRepository, storeRepository);
        this.categoryService = new CategoryServiceImpl(categoryRepository, categoryValidator);
        this.categoryController = new CategoryController(categoryService);
    }

    public void setSecurityContextAuthentication(final Username username) {
        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, "");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public void setTimeHolder(final LocalDateTime fixedTime) {
        this.timeHolder.setMillis(fixedTime);
    }

    public void setUuidHolder(final String uuidInput) {
        this.uuidHolder.setUuidInput(uuidInput);
    }
}
