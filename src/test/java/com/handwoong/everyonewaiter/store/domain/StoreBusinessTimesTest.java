package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBusinessTimeEntity;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreBusinessTimesTest {

    @Test
    void Should_GetListEntity_When_ToEntity() {
        // given
        final StoreBusinessTimes storeBusinessTimes = new StoreBusinessTimes(
            List.of(
                StoreBusinessTime.builder()
                    .id(new StoreBusinessTimeId(1L))
                    .open(LocalTime.of(11, 0, 0))
                    .close(LocalTime.of(21, 0, 0))
                    .daysOfWeek(
                        new StoreDaysOfWeek(
                            List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                        )
                    )
                    .build(),
                StoreBusinessTime.builder()
                    .id(new StoreBusinessTimeId(2L))
                    .open(LocalTime.of(9, 0, 0))
                    .close(LocalTime.of(21, 0, 0))
                    .daysOfWeek(
                        new StoreDaysOfWeek(
                            List.of(SATURDAY, SUNDAY)
                        )
                    )
                    .build()
            )
        );

        // when
        final List<StoreBusinessTimeEntity> storeBusinessTimeEntities = storeBusinessTimes.toEntity();

        // then
        assertThat(storeBusinessTimeEntities).hasSize(2);
        assertThat(storeBusinessTimeEntities).extracting("id").contains(1L, 2L);
    }

    @Test
    void Should_ThrowException_When_DaysSizeGreaterThanDaysOfWeekSize() {
        // given
        final List<StoreBusinessTime> businessTimes = List.of(
            StoreBusinessTime.builder()
                .id(new StoreBusinessTimeId(1L))
                .open(LocalTime.of(11, 0, 0))
                .close(LocalTime.of(21, 0, 0))
                .daysOfWeek(
                    new StoreDaysOfWeek(
                        List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                    )
                )
                .build(),
            StoreBusinessTime.builder()
                .id(new StoreBusinessTimeId(2L))
                .open(LocalTime.of(9, 0, 0))
                .close(LocalTime.of(21, 0, 0))
                .daysOfWeek(
                    new StoreDaysOfWeek(
                        List.of(MONDAY, SATURDAY, SUNDAY)
                    )
                )
                .build()
        );

        // expect
        assertThatThrownBy(() -> new StoreBusinessTimes(businessTimes))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("영업 시간의 요일은 " + DayOfWeek.size() + "개까지 선택할 수 있습니다.");
    }

    @Test
    void Should_ThrowException_When_DuplicateDays() {
        // given
        final List<StoreBusinessTime> businessTimes = List.of(
            StoreBusinessTime.builder()
                .id(new StoreBusinessTimeId(1L))
                .open(LocalTime.of(11, 0, 0))
                .close(LocalTime.of(21, 0, 0))
                .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
                .build(),
            StoreBusinessTime.builder()
                .id(new StoreBusinessTimeId(2L))
                .open(LocalTime.of(9, 0, 0))
                .close(LocalTime.of(21, 0, 0))
                .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
                .build()
        );

        // expect
        assertThatThrownBy(() -> new StoreBusinessTimes(businessTimes))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("영업 시간에 중복된 요일이 있습니다.");
    }
}
