package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakeUuidHolder;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingTest {

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
