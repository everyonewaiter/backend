package com.handwoong.everyonewaiter.store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class DayOfWeekTest {

    @ParameterizedTest
    @ValueSource(strings = {"월", "화", "수", "목", "금", "토", "일"})
    void Should_GetDayOfWeek_When_From(final String value) {
        // given
        // when
        final DayOfWeek dayOfWeek = DayOfWeek.from(value);

        // then
        assertThat(dayOfWeek.toString()).hasToString(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"})
    void Should_ThrowException_When_NotFoundValue(final String value) {
        // expect
        assertThatThrownBy(() -> DayOfWeek.from(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(value + "(은)는 유효하지 않은 요일입니다.");
    }

    @Test
    void Should_7_When_Size() {
        // given
        // when
        final int size = DayOfWeek.size();

        // then
        assertThat(size).isEqualTo(7);
    }

    @ParameterizedTest(name = "요일 {index} : {0}")
    @EnumSource
    void Should_HasKey_When_DayOfWeekCounter(final DayOfWeek dayOfWeek) {
        // given
        // when
        final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();

        // then
        assertThat(counter).containsEntry(dayOfWeek, 0);
    }
}
