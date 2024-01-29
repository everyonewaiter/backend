package com.handwoong.everyonewaiter.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import com.handwoong.everyonewaiter.user.exception.AlreadyExistsUsernameException;
import com.handwoong.everyonewaiter.user.mock.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

    private FakeUserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        final FakePasswordEncoder passwordEncoder = new FakePasswordEncoder("encode");
        userRepository = new FakeUserRepository();
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void Should_Join_When_ValidUserJoin() {
        // given
        final UserJoin userJoin = UserJoin.builder()
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .build();

        // when
        final Long userId = userService.join(userJoin);

        // then
        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void Should_ThrowException_When_DuplicateUsername() {
        // given
        final User user = User.builder()
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .role(UserRole.ROLE_USER)
            .status(UserStatus.ACTIVE)
            .build();
        userRepository.save(user);

        final UserJoin userJoin = UserJoin.builder()
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .build();

        // expect
        assertThatThrownBy(() -> userService.join(userJoin))
            .isInstanceOf(AlreadyExistsUsernameException.class)
            .hasMessage("이미 존재하는 사용자 아이디입니다.");
    }
}
