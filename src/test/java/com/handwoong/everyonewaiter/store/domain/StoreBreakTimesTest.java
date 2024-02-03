package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.infrastructure.StoreBreakTimeEntity;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreBreakTimesTest {

    @Test
    void Should_GetListEntity_When_ToEntity() {
        // given
        final StoreBreakTimes storeBreakTimes = new StoreBreakTimes(
            List.of(
                StoreBreakTime.builder()
                    .id(new StoreBreakTimeId(1L))
                    .start(LocalTime.of(15, 0, 0))
                    .end(LocalTime.of(16, 30, 0))
                    .daysOfWeek(
                        new StoreEventDaysOfWeek(
                            List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                        )
                    )
                    .build(),
                StoreBreakTime.builder()
                    .id(new StoreBreakTimeId(2L))
                    .start(LocalTime.of(15, 30, 0))
                    .end(LocalTime.of(17, 0, 0))
                    .daysOfWeek(
                        new StoreEventDaysOfWeek(
                            List.of(SATURDAY, SUNDAY)
                        )
                    )
                    .build()
            )
        );

        // when
        final List<StoreBreakTimeEntity> storeBreakTimeEntities = storeBreakTimes.toEntity();

        // then
        assertThat(storeBreakTimeEntities).hasSize(2);
        assertThat(storeBreakTimeEntities).extracting("id").contains(1L, 2L);
    }
}
