package com.handwoong.everyonewaiter.store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class StoreEventDaysOfWeekTest {

    @Test
    void Should_JoinString_When_InputDelimiter() {
        // given
        final StoreEventDaysOfWeek storeEventDaysOfWeek = new StoreEventDaysOfWeek(
            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
        );

        // when
        final String result = storeEventDaysOfWeek.toString("::");

        // then
        assertThat(result).isEqualTo("월::화::수::목::금");
    }
}