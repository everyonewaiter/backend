package com.handwoong.everyonewaiter.waiting.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import com.handwoong.everyonewaiter.waiting.dto.WaitingGenerateInfo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingGeneratorTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
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
            .lastOpenedAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0))
            .build();
        testContainer.userRepository.save(user);
        testContainer.storeRepository.save(store);
    }

    @Test
    void Should_Number1AndTurn0_When_First() {
        // given
        final StoreId storeId = new StoreId(1L);

        // when
        final WaitingGenerateInfo result = testContainer.waitingGenerator.generate(storeId);

        // then
        assertThat(result.number().value()).isEqualTo(1);
        assertThat(result.turn().value()).isZero();
    }

    @Test
    void Should_Number2AndTurn1_When_Second() {
        // given
        final StoreId storeId = new StoreId(1L);
        final Waiting waiting = Waiting.builder()
            .storeId(storeId)
            .status(WaitingStatus.WAIT)
            .timestamp(
                DomainTimestamp.builder()
                    .createdAt(LocalDateTime.now())
                    .build()
            )
            .build();
        testContainer.waitingRepository.save(waiting);

        // when
        final WaitingGenerateInfo result = testContainer.waitingGenerator.generate(storeId);

        // then
        assertThat(result.number().value()).isEqualTo(2);
        assertThat(result.turn().value()).isEqualTo(1);
    }

    @Test
    void Should_Number3AndTurn1_When_IncludeCancel() {
        // given
        final StoreId storeId = new StoreId(1L);
        final Waiting waiting1 = Waiting.builder()
            .storeId(storeId)
            .status(WaitingStatus.WAIT)
            .timestamp(
                DomainTimestamp.builder()
                    .createdAt(LocalDateTime.now())
                    .build()
            )
            .build();
        final Waiting waiting2 = Waiting.builder()
            .storeId(storeId)
            .status(WaitingStatus.CANCEL)
            .timestamp(
                DomainTimestamp.builder()
                    .createdAt(LocalDateTime.now())
                    .build()
            )
            .build();
        testContainer.waitingRepository.save(waiting1);
        testContainer.waitingRepository.save(waiting2);

        // when
        final WaitingGenerateInfo result = testContainer.waitingGenerator.generate(storeId);

        // then
        assertThat(result.number().value()).isEqualTo(3);
        assertThat(result.turn().value()).isEqualTo(1);
    }
}
