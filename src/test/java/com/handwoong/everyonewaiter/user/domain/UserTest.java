package com.handwoong.everyonewaiter.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

class UserTest {

    @Test
    void Should_CreateDomain_When_UserJoin() {
        // given
        final FakePasswordEncoder passwordEncoder = new FakePasswordEncoder("encode");
        final UserJoin userJoin = UserJoin.builder()
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .build();

        // when
        final User user = User.create(userJoin, passwordEncoder);

        // then
        assertThat(user).extracting("username").hasToString("handwoong");
        assertThat(user).extracting("password").hasToString("password" + "encode");
        assertThat(user).extracting("phoneNumber").hasToString("01012345678");
    }

    @Test
    void Should_SetLastLoggedIn_When_Login() {
        // given
        final FakeTimeHolder timeHolder = new FakeTimeHolder(948920669L);
        final User user = User.builder()
            .id(1L)
            .username(new Username("handwoong"))
            .password(new Password("password"))
            .phoneNumber(new PhoneNumber("01012345678"))
            .role(UserRole.ROLE_USER)
            .status(UserStatus.ACTIVE)
            .build();

        // when
        final User loggedInUser = user.login(timeHolder);

        // then
        assertThat(loggedInUser).extracting("lastLoggedIn").isEqualTo(948920669L);
    }

    @ParameterizedTest(name = "회원 상태 {index} : {0}")
    @EnumSource(mode = Mode.EXCLUDE, names = {"ACTIVE"})
    void Should_False_When_StatusNotMatched(final UserStatus status) {
        // given
        final User user = User.builder()
            .status(UserStatus.ACTIVE)
            .build();

        // when
        final boolean result = user.checkStatusDifference(status);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest(name = "회원 상태 {index} : {0}")
    @EnumSource
    void Should_True_When_StatusMatched(final UserStatus status) {
        // given
        final User user = User.builder()
            .status(status)
            .build();

        // when
        final boolean result = user.checkStatusDifference(status);

        // then
        assertThat(result).isFalse();
    }
}
