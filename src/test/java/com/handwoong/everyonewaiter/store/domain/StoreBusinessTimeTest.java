package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StoreBusinessTimeTest {

    @Test
    void Should_IncreaseCount_When_MatchedDayOfWeek() {
        // given
        final Map<DayOfWeek, Integer> counter = DayOfWeek.dayOfWeekCounter();
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(9, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)))
            .build();

        // when
        storeBusinessTime.daysCount(counter);

        // then
        assertThat(counter)
            .containsEntry(MONDAY, 1)
            .containsEntry(TUESDAY, 1)
            .containsEntry(WEDNESDAY, 1)
            .containsEntry(THURSDAY, 1)
            .containsEntry(FRIDAY, 1)
            .containsEntry(SATURDAY, 1)
            .containsEntry(SUNDAY, 1);
    }

    @Test
    void Should_7_When_GetDaysSize() {
        // given
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(9, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)))
            .build();

        // when
        final int result = storeBusinessTime.getDaysSize();

        // then
        assertThat(result).isEqualTo(7);
    }

    @Test
    void Should_True_When_CompareWithinTime() {
        // given
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(9, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)))
            .build();

        // when
        final boolean result = storeBusinessTime.compareCurrentTime(MONDAY, LocalTime.of(15, 0, 0));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_CompareWithinTime() {
        // given
        final StoreBusinessTime storeBusinessTime = StoreBusinessTime.builder()
            .id(new StoreBusinessTimeId(1L))
            .open(LocalTime.of(9, 0, 0))
            .close(LocalTime.of(21, 0, 0))
            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)))
            .build();

        // when
        final boolean result = storeBusinessTime.compareCurrentTime(MONDAY, LocalTime.of(22, 0, 0));

        // then
        assertThat(result).isFalse();
    }
}
