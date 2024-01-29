package com.handwoong.everyonewaiter.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakePasswordEncoder;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import org.junit.jupiter.api.Test;

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
}
