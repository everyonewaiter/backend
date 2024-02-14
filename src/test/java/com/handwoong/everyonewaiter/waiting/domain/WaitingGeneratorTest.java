package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static com.handwoong.everyonewaiter.util.Fixtures.aWaiting;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
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
        final Username username = new Username("handwoong");
        testContainer = new TestContainer();
        testContainer.setSecurityContextAuthentication(username);

        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().lastOpenedAt(LocalDateTime.of(1970, 1, 1, 0, 0, 0)).build();
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
        final Waiting waiting = aWaiting().build();
        testContainer.waitingRepository.save(waiting);

        // when
        final WaitingGenerateInfo result = testContainer.waitingGenerator.generate(new StoreId(1L));

        // then
        assertThat(result.number().value()).isEqualTo(2);
        assertThat(result.turn().value()).isEqualTo(1);
    }

    @Test
    void Should_Number3AndTurn1_When_IncludeCancel() {
        // given
        final Waiting waiting1 = aWaiting().build();
        testContainer.waitingRepository.save(waiting1);

        final Waiting waiting2 = aWaiting().id(new WaitingId(2L)).status(WaitingStatus.CANCEL).build();
        testContainer.waitingRepository.save(waiting2);

        // when
        final WaitingGenerateInfo result = testContainer.waitingGenerator.generate(new StoreId(1L));

        // then
        assertThat(result.number().value()).isEqualTo(3);
        assertThat(result.turn().value()).isEqualTo(1);
    }
}
