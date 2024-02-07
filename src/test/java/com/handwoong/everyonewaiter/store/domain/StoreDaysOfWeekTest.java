package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.dayOfWeekCounter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StoreDaysOfWeekTest {

    @Test
    void Should_JoinString_When_InputDelimiter() {
        // given
        final StoreDaysOfWeek storeDaysOfWeek =
            new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));

        // when
        final String result = storeDaysOfWeek.toString("::");

        // then
        assertThat(result).isEqualTo("월::화::수::목::금");
    }

    @Test
    void Should_ThrowException_When_DuplicateDayOfWeek() {
        // given
        final List<DayOfWeek> daysOfWeek = List.of(MONDAY, MONDAY);

        // expect
        assertThatThrownBy(() -> new StoreDaysOfWeek(daysOfWeek))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("요일은 중복될 수 없습니다.");
    }

    @Test
    void Should_5_When_GetDaysSize() {
        // given
        final StoreDaysOfWeek storeDaysOfWeek =
            new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));

        // when
        final int daysSize = storeDaysOfWeek.getDaysSize();

        // then
        assertThat(daysSize).isEqualTo(5);
    }

    @Test
    void Should_IncreaseCount_When_InputCounter() {
        // given
        final Map<DayOfWeek, Integer> counter = dayOfWeekCounter();
        final StoreDaysOfWeek storeDaysOfWeek =
            new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));

        // when
        storeDaysOfWeek.count(counter);

        // then
        assertThat(counter)
            .containsEntry(MONDAY, 1)
            .containsEntry(TUESDAY, 1)
            .containsEntry(WEDNESDAY, 1)
            .containsEntry(THURSDAY, 1)
            .containsEntry(FRIDAY, 1)
            .containsEntry(SATURDAY, 0)
            .containsEntry(SUNDAY, 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"월", "화", "수", "목", "금", "토", "일"})
    void Should_True_When_Contains(final String value) {
        // given
        final DayOfWeek dayOfWeek = DayOfWeek.from(value);
        final StoreDaysOfWeek storeDaysOfWeek =
            new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));

        // when
        final boolean result = storeDaysOfWeek.contains(dayOfWeek);

        // then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"화", "수", "목", "금", "토", "일"})
    void Should_False_When_Contains(final String value) {
        // given
        final DayOfWeek dayOfWeek = DayOfWeek.from(value);
        final StoreDaysOfWeek storeDaysOfWeek =
            new StoreDaysOfWeek(List.of(MONDAY));

        // when
        final boolean result = storeDaysOfWeek.contains(dayOfWeek);

        // then
        assertThat(result).isFalse();
    }
}
