package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StoreTest {

    private StoreCreate storeCreate;

    @BeforeEach
    void setUp() {
        storeCreate = StoreCreate.builder()
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .businessTimes(new StoreBusinessTimes(List.of()))
            .breakTimes(new StoreBreakTimes(List.of()))
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .useOrder(true)
                    .build()
            )
            .build();
    }

    @Test
    void Should_NewStore_When_Create() {
        // given
        final UserId userId = new UserId(1L);

        // when
        final Store store = Store.create(userId, storeCreate);

        // then
        assertThat(store.getId()).isNull();
        assertThat(store.getUserId()).isEqualTo(userId);
        assertThat(store.getName()).hasToString("나루");
        assertThat(store.getLandlineNumber()).hasToString("0551234567");
        assertThat(store.getStatus()).isEqualTo(StoreStatus.CLOSE);
        assertThat(store.getBreakTimes().breakTimes()).isEmpty();
        assertThat(store.getBusinessTimes().businessTimes()).isEmpty();
        assertThat(store.getOption())
            .extracting("useBreakTime", "useWaiting", "useOrder")
            .containsExactly(true, true, true);
    }

    @Test
    void Should_UpdateStoreInfo_When_Update() {
        // given
        final UserId userId = new UserId(1L);
        final Store store = Store.create(userId, storeCreate);
        final StoreBusinessTimes businessTimes = new StoreBusinessTimes(
            List.of(
                StoreBusinessTime.builder()
                    .id(new StoreBusinessTimeId(1L))
                    .open(LocalTime.of(11, 0, 0))
                    .close(LocalTime.of(21, 0, 0))
                    .daysOfWeek(
                        new StoreDaysOfWeek(
                            List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
                        )
                    )
                    .build()
            )
        );
        final StoreBreakTimes breakTimes = new StoreBreakTimes(
            List.of(
                StoreBreakTime.builder()
                    .id(new StoreBreakTimeId(1L))
                    .start(LocalTime.of(15, 0, 0))
                    .end(LocalTime.of(16, 30, 0))
                    .daysOfWeek(
                        new StoreDaysOfWeek(
                            List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                        )
                    )
                    .build(),
                StoreBreakTime.builder()
                    .id(new StoreBreakTimeId(2L))
                    .start(LocalTime.of(15, 30, 0))
                    .end(LocalTime.of(17, 0, 0))
                    .daysOfWeek(
                        new StoreDaysOfWeek(
                            List.of(SATURDAY, SUNDAY)
                        )
                    )
                    .build()
            )
        );

        final StoreUpdate storeUpdate = StoreUpdate.builder()
            .name(new StoreName("나루 레스토랑"))
            .landlineNumber(new LandlineNumber("021234567"))
            .businessTimes(businessTimes)
            .breakTimes(breakTimes)
            .build();

        // when
        final Store result = store.update(storeUpdate);

        // then
        assertThat(result.getId()).isNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getName()).hasToString("나루 레스토랑");
        assertThat(result.getLandlineNumber()).hasToString("021234567");
        assertThat(result.getStatus()).isEqualTo(StoreStatus.CLOSE);
        assertThat(result.getBreakTimes()).isEqualTo(breakTimes);
        assertThat(result.getBusinessTimes()).isEqualTo(businessTimes);
        assertThat(result.getOption())
            .extracting("useBreakTime", "useWaiting", "useOrder")
            .containsExactly(true, true, true);
    }

    @Test
    void Should_UpdateStoreOption_When_Update() {
        //given
        final UserId userId = new UserId(1L);
        final Store store = Store.create(userId, storeCreate);
        final StoreOptionUpdate optionUpdate = new StoreOptionUpdate(store.getId(), false, false, false);

        //when
        final Store updatedStore = store.update(optionUpdate);

        //then
        assertThat(updatedStore.getOption().isUseBreakTime()).isFalse();
        assertThat(updatedStore.getOption().isUseWaiting()).isFalse();
        assertThat(updatedStore.getOption().isUseOrder()).isFalse();
    }

    @Test
    void Should_True_When_Open() {
        // given
        final Store store = Store.builder()
            .status(StoreStatus.OPEN)
            .build();

        // when
        final boolean result = store.isOpen();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_Close() {
        // given
        final Store store = Store.builder()
            .status(StoreStatus.CLOSE)
            .build();

        // when
        final boolean result = store.isOpen();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_True_When_UseWaiting() {
        // given
        final Store store = Store.builder()
            .option(
                StoreOption.builder()
                    .useWaiting(true)
                    .build()
            )
            .build();

        // when
        final boolean result = store.isUseWaiting();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_UnUseWaiting() {
        // given
        final Store store = Store.builder()
            .option(
                StoreOption.builder()
                    .useWaiting(false)
                    .build()
            )
            .build();

        // when
        final boolean result = store.isUseWaiting();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_True_When_UseBreakTime() {
        // given
        final Store store = Store.builder()
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .build()
            )
            .build();

        // when
        final boolean result = store.isUseBreakTime();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_UnUseBreakTime() {
        // given
        final Store store = Store.builder()
            .option(
                StoreOption.builder()
                    .useBreakTime(false)
                    .build()
            )
            .build();

        // when
        final boolean result = store.isUseBreakTime();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_True_When_IsWithinBreakTime() {
        // given
        final FakeTimeHolder timeHolder = new FakeTimeHolder();
        final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 16, 0, 0); // 수요일 16시 0분
        timeHolder.setMillis(mockTime);

        final Store store = Store.builder()
            .breakTimes(
                new StoreBreakTimes(
                    List.of(
                        StoreBreakTime.builder()
                            .id(new StoreBreakTimeId(1L))
                            .start(LocalTime.of(15, 0, 0))
                            .end(LocalTime.of(16, 30, 0))
                            .daysOfWeek(
                                new StoreDaysOfWeek(
                                    List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                                )
                            )
                            .build()
                    )
                )
            )
            .build();

        // when
        final boolean result = store.isWithinBreakTime(timeHolder);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_IsWithinBreakTime() {
        // given
        final FakeTimeHolder timeHolder = new FakeTimeHolder();
        final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 16, 40, 0); // 수요일 16시 40분
        timeHolder.setMillis(mockTime);

        final Store store = Store.builder()
            .breakTimes(
                new StoreBreakTimes(
                    List.of(
                        StoreBreakTime.builder()
                            .id(new StoreBreakTimeId(1L))
                            .start(LocalTime.of(15, 0, 0))
                            .end(LocalTime.of(16, 30, 0))
                            .daysOfWeek(
                                new StoreDaysOfWeek(
                                    List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
                                )
                            )
                            .build()
                    )
                )
            )
            .build();

        // when
        final boolean result = store.isWithinBreakTime(timeHolder);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_True_When_IsWithinBusinessTime() {
        // given
        final FakeTimeHolder timeHolder = new FakeTimeHolder();
        final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 18, 0, 0); // 수요일 18시 0분
        timeHolder.setMillis(mockTime);

        final Store store = Store.builder()
            .businessTimes(
                new StoreBusinessTimes(
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
                )
            )
            .build();

        // when
        final boolean result = store.isWithinBusinessTime(timeHolder);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_IsWithinBusinessTime() {
        // given
        final FakeTimeHolder timeHolder = new FakeTimeHolder();
        final LocalDateTime mockTime = LocalDateTime.of(2024, 2, 7, 22, 0, 0); // 수요일 22시 0분
        timeHolder.setMillis(mockTime);

        final Store store = Store.builder()
            .businessTimes(
                new StoreBusinessTimes(
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
                )
            )
            .build();

        // when
        final boolean result = store.isWithinBusinessTime(timeHolder);

        // then
        assertThat(result).isFalse();
    }
}
