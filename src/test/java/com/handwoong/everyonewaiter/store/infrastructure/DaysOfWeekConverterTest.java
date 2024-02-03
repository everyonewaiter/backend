package com.handwoong.everyonewaiter.store.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.DayOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreEventDaysOfWeek;
import java.util.List;
import org.junit.jupiter.api.Test;

class DaysOfWeekConverterTest {

    @Test
    void Should_ConvertString_When_InputStoreEventDaysOfWeek() {
        // given
        final DaysOfWeekConverter converter = new DaysOfWeekConverter();
        final StoreEventDaysOfWeek storeEventDaysOfWeek = new StoreEventDaysOfWeek(
            List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
        );

        // when
        final String convertedDaysOfWeek = converter.convertToDatabaseColumn(storeEventDaysOfWeek);

        // then
        assertThat(convertedDaysOfWeek).isEqualTo("월,화,수,목,금");
    }

    @Test
    void Should_ConvertStoreEventDaysOfWeek_When_InputString() {
        // given
        final DaysOfWeekConverter converter = new DaysOfWeekConverter();
        final String daysOfWeek = "월,화";

        // when
        final StoreEventDaysOfWeek storeEventDaysOfWeek = converter.convertToEntityAttribute(daysOfWeek);

        // then
        assertThat(storeEventDaysOfWeek)
            .extracting("daysOfWeek")
            .isEqualTo(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
    }

    @Test
    void Should_ThrowException_When_InvalidInputString() {
        // given
        final DaysOfWeekConverter converter = new DaysOfWeekConverter();
        final String daysOfWeek = "월요일,화요일";

        // expect
        assertThatThrownBy(() -> converter.convertToEntityAttribute(daysOfWeek))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("월요일(은)는 유효하지 않은 요일입니다.");
    }
}
