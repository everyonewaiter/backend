package com.handwoong.everyonewaiter.waiting.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.mock.FakeUuidHolder;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import com.handwoong.everyonewaiter.waiting.domain.WaitingTurn;
import org.junit.jupiter.api.Test;

class WaitingEntityTest {

    @Test
    void Should_CreateEntity_When_FromModel() {
        // given
        final FakeUuidHolder uuidHolder = new FakeUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        final Waiting waiting = Waiting.builder()
            .id(new WaitingId(1L))
            .storeId(new StoreId(1L))
            .adult(new WaitingAdult(2))
            .children(new WaitingChildren(0))
            .number(new WaitingNumber(10))
            .turn(new WaitingTurn(9))
            .phoneNumber(new PhoneNumber("01012345678"))
            .status(WaitingStatus.WAIT)
            .notificationType(WaitingNotificationType.REGISTER)
            .uniqueCode(uuidHolder.generate())
            .build();

        // when
        final WaitingEntity waitingEntity = WaitingEntity.from(waiting);

        // then
        assertThat(waitingEntity.getId()).isEqualTo(1L);
        assertThat(waitingEntity.getStoreId()).isEqualTo(1L);
    }

    @Test
    void Should_CreateDomain_When_ToModel() {
        // given
        final FakeUuidHolder uuidHolder = new FakeUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        final Waiting waiting = Waiting.builder()
            .id(new WaitingId(1L))
            .storeId(new StoreId(1L))
            .adult(new WaitingAdult(2))
            .children(new WaitingChildren(0))
            .number(new WaitingNumber(10))
            .turn(new WaitingTurn(9))
            .phoneNumber(new PhoneNumber("01012345678"))
            .status(WaitingStatus.WAIT)
            .notificationType(WaitingNotificationType.REGISTER)
            .uniqueCode(uuidHolder.generate())
            .build();
        final WaitingEntity waitingEntity = WaitingEntity.from(waiting);

        // when
        final Waiting result = waitingEntity.toModel();

        // then
        assertThat(result.getId().value()).isEqualTo(1L);
        assertThat(result.getStoreId().value()).isEqualTo(1L);
    }
}
