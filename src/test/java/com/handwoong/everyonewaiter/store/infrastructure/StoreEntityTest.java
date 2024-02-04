package com.handwoong.everyonewaiter.store.infrastructure;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreOptionId;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .status(StoreStatus.CLOSE)
            .businessTimes(
                new StoreBusinessTimes(
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
                )
            )
            .breakTimes(
                new StoreBreakTimes(
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
                )
            )
            .option(
                StoreOption.builder()
                    .id(new StoreOptionId(1L))
                    .useBreakTime(true)
                    .useWaiting(true)
                    .useOrder(true)
                    .build()
            )
            .build();

        // when
        final StoreEntity storeEntity = StoreEntity.from(store);

        // then
        assertThat(storeEntity).extracting("id").isEqualTo(1L);
        assertThat(storeEntity.getBusinessTimeEntities()).extracting("id").contains(1L);
        assertThat(storeEntity.getBreakTimeEntities()).extracting("id").contains(1L, 2L);
        assertThat(storeEntity.getOptionEntity()).extracting("id").isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .status(StoreStatus.CLOSE)
            .businessTimes(
                new StoreBusinessTimes(
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
                )
            )
            .breakTimes(
                new StoreBreakTimes(
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
                )
            )
            .option(
                StoreOption.builder()
                    .id(new StoreOptionId(1L))
                    .useBreakTime(true)
                    .useWaiting(true)
                    .useOrder(true)
                    .build()
            )
            .build();
        final StoreEntity storeEntity = StoreEntity.from(store);

        // when
        final Store result = storeEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
    }

    @Test
    void Should_ThrowException_When_UserIdIsNull() {
        // given
        final Store store = Store.builder().build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("매장의 사용자 ID는 null일 수 없습니다.");
    }

    @Test
    void Should_ThrowException_When_FromModelStoreNameIsNull() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void Should_ThrowException_When_FromModelLandlineNumberIsNull() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void Should_ThrowException_When_FromModelBusinessTimesIsNull() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void Should_ThrowException_When_FromModelBreakTimesIsNull() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .businessTimes(new StoreBusinessTimes(List.of()))
            .build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void Should_ThrowException_When_FromModelStoreOptionIsNull() {
        // given
        final Store store = Store.builder()
            .id(new StoreId(1L))
            .userId(new UserId(1L))
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .businessTimes(new StoreBusinessTimes(List.of()))
            .breakTimes(new StoreBreakTimes(List.of()))
            .build();

        // expect
        assertThatThrownBy(() -> StoreEntity.from(store))
            .isInstanceOf(NullPointerException.class);
    }
}
