package com.handwoong.everyonewaiter.store.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBreakTime;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreBusinessTime;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreOption;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.mock.FakeTimeHolder;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void Should_NewStore_When_Create() {
        // given
        final UserId userId = new UserId(1L);
        final StoreCreate storeCreate = StoreCreate.builder()
            .name(new StoreName("나루"))
            .landlineNumber(new LandlineNumber("0551234567"))
            .businessTimes(new StoreBusinessTimes(List.of()))
            .breakTimes(new StoreBreakTimes(List.of()))
            .option(aStoreOption().build())
            .build();

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
        final Store store = aStore()
            .businessTimes(new StoreBusinessTimes(List.of()))
            .breakTimes(new StoreBreakTimes(List.of()))
            .build();
        final StoreBusinessTimes businessTimes = new StoreBusinessTimes(List.of(aStoreBusinessTime().build()));
        final StoreBreakTimes breakTimes = new StoreBreakTimes(List.of(aStoreBreakTime().build()));

        final StoreUpdate storeUpdate = StoreUpdate.builder()
            .name(new StoreName("나루 레스토랑"))
            .landlineNumber(new LandlineNumber("021234567"))
            .businessTimes(businessTimes)
            .breakTimes(breakTimes)
            .build();

        // when
        final Store result = store.update(storeUpdate);

        // then
        assertThat(result.getName()).hasToString("나루 레스토랑");
        assertThat(result.getLandlineNumber()).hasToString("021234567");
        assertThat(result.getBreakTimes()).isEqualTo(breakTimes);
        assertThat(result.getBusinessTimes()).isEqualTo(businessTimes);
    }

    @Test
    void Should_UpdateStoreOption_When_Update() {
        //given
        final Store store = aStore().build();
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
        final Store store = aStore().status(StoreStatus.OPEN).build();

        // when
        final boolean result = store.isOpen();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_Close() {
        // given
        final Store store = aStore().status(StoreStatus.CLOSE).build();

        // when
        final boolean result = store.isOpen();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_True_When_UseWaiting() {
        // given
        final Store store = aStore().build();

        // when
        final boolean result = store.isUseWaiting();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_UnUseWaiting() {
        // given
        final Store store = aStore()
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
        final Store store = aStore().build();

        // when
        final boolean result = store.isUseBreakTime();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_False_When_UnUseBreakTime() {
        // given
        final Store store = aStore()
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

        final Store store = aStore().build();

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

        final Store store = aStore().build();

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

        final Store store = aStore().build();

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

        final Store store = aStore().build();

        // when
        final boolean result = store.isWithinBusinessTime(timeHolder);

        // then
        assertThat(result).isFalse();
    }
}
