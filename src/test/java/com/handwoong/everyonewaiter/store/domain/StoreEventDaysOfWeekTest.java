package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class StoreEventDaysOfWeekTest {

    @Test
    void Should_JoinString_When_InputDelimiter() {
        // given
        final StoreEventDaysOfWeek storeEventDaysOfWeek =
            new StoreEventDaysOfWeek(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));

        // when
        final String result = storeEventDaysOfWeek.toString("::");

        // then
        assertThat(result).isEqualTo("월::화::수::목::금");
    }

    @Test
    void Should_ThrowException_When_DuplicateDayOfWeek() {
        // given
        final List<DayOfWeek> daysOfWeek = List.of(MONDAY, MONDAY);
        
        // expect
        assertThatThrownBy(() -> new StoreEventDaysOfWeek(daysOfWeek))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("요일은 중복될 수 없습니다.");
    }
}
