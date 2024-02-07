package com.handwoong.everyonewaiter.waiting.application;

import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.MONDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
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
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import com.handwoong.everyonewaiter.waiting.exception.AlreadyExistsPhoneNumberException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingServiceImplTest {

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
    void Should_Register_When_ValidWaitingRegister() {
        // given
        final WaitingRegister waitingRegister = WaitingRegister.builder()
            .storeId(new StoreId(1L))
            .adult(new WaitingAdult(2))
            .children(new WaitingChildren(0))
            .phoneNumber(new PhoneNumber("01012345678"))
            .build();

        // when
        final WaitingId waitingId = testContainer.waitingService.register(waitingRegister);

        // then
        assertThat(waitingId.value()).isEqualTo(1L);
    }

    @Test
    void Should_ThrowException_When_DuplicatePhoneNumber() {
        // given
        final PhoneNumber phoneNumber = new PhoneNumber("01012345678");
        final Waiting waiting = Waiting.builder()
            .status(WaitingStatus.WAIT)
            .phoneNumber(phoneNumber)
            .build();
        testContainer.waitingRepository.save(waiting);

        final WaitingRegister waitingRegister = WaitingRegister.builder()
            .phoneNumber(new PhoneNumber("01012345678"))
            .build();

        // expect
        assertThatThrownBy(() -> testContainer.waitingService.register(waitingRegister))
            .isInstanceOf(AlreadyExistsPhoneNumberException.class)
            .hasMessage("이미 웨이팅에 등록되어 있는 휴대폰 번호입니다.");
    }
}
