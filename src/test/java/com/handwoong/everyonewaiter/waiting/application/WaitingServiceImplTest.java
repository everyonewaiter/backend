package com.handwoong.everyonewaiter.waiting.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static com.handwoong.everyonewaiter.util.Fixtures.aWaiting;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import com.handwoong.everyonewaiter.waiting.exception.AlreadyExistsPhoneNumberException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingServiceImplTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        final Username username = new Username("handwoong");
        testContainer = new TestContainer();
        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().build();
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
        final Waiting waiting = aWaiting().phoneNumber(phoneNumber).build();
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
