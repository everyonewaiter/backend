package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimeId;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreOption;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class WaitingValidatorTest {

    @Test
    void Should_DoesNotThrowException_When_Validate() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Username username = new Username("handwoong");
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

        final User user = User.builder()
            .id(userId)
            .username(username)
            .build();
        final Store store = Store.builder()
            .id(storeId)
            .userId(userId)
            .status(StoreStatus.OPEN)
            .businessTimes(
                new StoreBusinessTimes(
                    List.of(
                        StoreBusinessTime.builder()
                            .id(new StoreBusinessTimeId(1L))
                            .open(LocalTime.of(9, 0, 0))
                            .close(LocalTime.of(21, 0, 0))
                            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
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
                            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
                            .build()
                    )
                )
            )
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .build()
            )
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);

        // expect
        assertThatCode(() -> testContainer.waitingValidator.validate(storeId))
            .doesNotThrowAnyException();
    }

    @Test
    void Should_ThrowException_When_UnUseWaiting() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Username username = new Username("handwoong");
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        testContainer.setSecurityContextAuthentication(username);

        final User user = User.builder()
            .id(userId)
            .username(username)
            .build();
        final Store store = Store.builder()
            .id(storeId)
            .userId(userId)
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(false)
                    .build()
            )
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("웨이팅 기능을 사용하지 않는 매장입니다.");
    }

    @Test
    void Should_ThrowException_When_CloseStore() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Username username = new Username("handwoong");
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        testContainer.setSecurityContextAuthentication(username);

        final User user = User.builder()
            .id(userId)
            .username(username)
            .build();
        final Store store = Store.builder()
            .id(storeId)
            .userId(userId)
            .status(StoreStatus.CLOSE)
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .build()
            )
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장이 영업중이 아닙니다.");
    }

    @Test
    void Should_ThrowException_When_OverBusinessTime() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Username username = new Username("handwoong");
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 22, 0, 0)); // 월요일 22시 0분

        final User user = User.builder()
            .id(userId)
            .username(username)
            .build();
        final Store store = Store.builder()
            .id(storeId)
            .userId(userId)
            .status(StoreStatus.OPEN)
            .businessTimes(
                new StoreBusinessTimes(
                    List.of(
                        StoreBusinessTime.builder()
                            .id(new StoreBusinessTimeId(1L))
                            .open(LocalTime.of(9, 0, 0))
                            .close(LocalTime.of(21, 0, 0))
                            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
                            .build()
                    )
                )
            )
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .build()
            )
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장 영업 시간이 아닙니다.");
    }

    @Test
    void Should_ThrowException_When_WithInBreakTime() {
        // given
        final TestContainer testContainer = new TestContainer();
        final Username username = new Username("handwoong");
        final UserId userId = new UserId(1L);
        final StoreId storeId = new StoreId(1L);

        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 15, 30, 0)); // 월요일 15시 30분

        final User user = User.builder()
            .id(userId)
            .username(username)
            .build();
        final Store store = Store.builder()
            .id(storeId)
            .userId(userId)
            .status(StoreStatus.OPEN)
            .businessTimes(
                new StoreBusinessTimes(
                    List.of(
                        StoreBusinessTime.builder()
                            .id(new StoreBusinessTimeId(1L))
                            .open(LocalTime.of(9, 0, 0))
                            .close(LocalTime.of(21, 0, 0))
                            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
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
                            .daysOfWeek(new StoreDaysOfWeek(List.of(MONDAY)))
                            .build()
                    )
                )
            )
            .option(
                StoreOption.builder()
                    .useBreakTime(true)
                    .useWaiting(true)
                    .build()
            )
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("브레이크 타임에는 웨이팅을 등록할 수 없습니다.");
    }
}
