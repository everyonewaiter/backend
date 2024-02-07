package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakeUuidHolder;
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
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
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
            .lastOpenedAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
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
    }

    @Test
    void Should_Create_When_Constructor() {
        // given
        final WaitingValidator waitingValidator = testContainer.waitingValidator;
        final WaitingGenerator waitingGenerator = testContainer.waitingGenerator;
        final FakeUuidHolder uuidHolder = testContainer.uuidHolder;

        final StoreId storeId = new StoreId(1L);
        final WaitingAdult adult = new WaitingAdult(2);
        final WaitingChildren children = new WaitingChildren(0);
        final PhoneNumber phoneNumber = new PhoneNumber("01012345678");
        final WaitingRegister waitingRegister = WaitingRegister.builder()
            .storeId(storeId)
            .adult(adult)
            .children(children)
            .phoneNumber(phoneNumber)
            .build();

        // when
        final Waiting waiting = new Waiting(waitingRegister, waitingValidator, waitingGenerator, uuidHolder);

        // then
        assertThat(waiting.getId()).isNull();
        assertThat(waiting.getStoreId()).isEqualTo(storeId);
        assertThat(waiting.getAdult()).isEqualTo(adult);
        assertThat(waiting.getChildren()).isEqualTo(children);
        assertThat(waiting.getNumber().value()).isEqualTo(1);
        assertThat(waiting.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(waiting.getStatus()).isEqualTo(WaitingStatus.WAIT);
        assertThat(waiting.getNotificationType()).isEqualTo(WaitingNotificationType.REGISTER);
        assertThat(waiting.getUniqueCode()).isEqualTo(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
    }
}
