package com.handwoong.everyonewaiter.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.user.exception.InvalidUsernameFormatException;
import org.junit.jupiter.api.Test;

class UsernameTest {

    @Test
    void Should_Create_When_ValidUsername() {
        // given
        final String username = "handwoong";

        // when
        final Username result = new Username(username);

        // then
        assertThat(result).hasToString(username);
    }

    @Test
    void Should_ThrowException_When_Null() {
        // expect
        assertThatThrownBy(() -> new Username(null))
            .isInstanceOf(InvalidUsernameFormatException.class)
            .hasMessage("사용자 아이디를 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_Blank() {
        // expect
        assertThatThrownBy(() -> new Username("  "))
            .isInstanceOf(InvalidUsernameFormatException.class)
            .hasMessage("사용자 아이디를 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_ContainsBlank() {
        // expect
        assertThatThrownBy(() -> new Username("hand  woong"))
            .isInstanceOf(InvalidUsernameFormatException.class)
            .hasMessage("사용자 아이디는 공백이 포함될 수 없습니다.");
    }

    @Test
    void Should_ThrowException_When_LengthGreaterThan30() {
        // given
        final String username = "handwoong".repeat(5);

        // expect
        assertThatThrownBy(() -> new Username(username))
            .isInstanceOf(InvalidUsernameFormatException.class)
            .hasMessage("사용자 아이디는 30자 이하로 입력해주세요.");
    }
}
