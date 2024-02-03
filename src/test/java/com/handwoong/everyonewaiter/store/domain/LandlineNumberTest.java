package com.handwoong.everyonewaiter.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.exception.InvalidLandlineNumberFormatException;
import org.junit.jupiter.api.Test;

class LandlineNumberTest {

    @Test
    void Should_Create_When_ValidLandlineNumber() {
        // given
        final String landlineNumber = "0551234567";

        // when
        final LandlineNumber result = new LandlineNumber(landlineNumber);

        // then
        assertThat(result).hasToString(landlineNumber);
    }

    @Test
    void Should_ThrowException_When_Null() {
        // expect
        assertThatThrownBy(() -> new LandlineNumber(null))
            .isInstanceOf(InvalidLandlineNumberFormatException.class)
            .hasMessage("매장 번호를 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_Blank() {
        // expect
        assertThatThrownBy(() -> new LandlineNumber("  "))
            .isInstanceOf(InvalidLandlineNumberFormatException.class)
            .hasMessage("매장 번호를 입력해주세요.");
    }

    @Test
    void Should_ThrowException_When_InvalidLandlineNumber() {
        // given
        final String landlineNumber = "01012345678";

        // expect
        assertThatThrownBy(() -> new LandlineNumber(landlineNumber))
            .isInstanceOf(InvalidLandlineNumberFormatException.class)
            .hasMessage("매장 번호의 형식이 옳바르지 않습니다.");
    }
}
