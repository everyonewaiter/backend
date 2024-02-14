package com.handwoong.everyonewaiter.waiting.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aStoreOption;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaitingValidatorTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();

        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().build();
        testContainer.storeRepository.save(store);
    }

    @Test
    void Should_DoesNotThrowException_When_Validate() {
        // given
        final Username username = new Username("handwoong");
        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

        final StoreId storeId = new StoreId(1L);

        // expect
        assertThatCode(() -> testContainer.waitingValidator.validate(storeId))
            .doesNotThrowAnyException();
    }

    @Test
    void Should_ThrowException_When_UnUseWaiting() {
        // given
        final Username username = new Username("handwoong");
        testContainer.setSecurityContextAuthentication(username);

        final StoreId storeId = new StoreId(2L);
        final Store store = aStore().id(storeId).option(aStoreOption().useWaiting(false).build()).build();
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("웨이팅 기능을 사용하지 않는 매장입니다.");
    }

    @Test
    void Should_ThrowException_When_CloseStore() {
        // given
        final Username username = new Username("handwoong");
        testContainer.setSecurityContextAuthentication(username);

        final StoreId storeId = new StoreId(1L);
        final Store store = aStore().id(storeId).status(StoreStatus.CLOSE).build();
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장이 영업중이 아닙니다.");
    }

    @Test
    void Should_ThrowException_When_OverBusinessTime() {
        // given
        final Username username = new Username("handwoong");
        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 22, 0, 0)); // 월요일 22시 0분

        final StoreId storeId = new StoreId(1L);
        final Store store = aStore().id(storeId).build();
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장 영업 시간이 아닙니다.");
    }

    @Test
    void Should_ThrowException_When_WithInBreakTime() {
        // given
        final Username username = new Username("handwoong");
        testContainer.setSecurityContextAuthentication(username);
        testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 15, 30, 0)); // 월요일 15시 30분

        final StoreId storeId = new StoreId(1L);
        final Store store = aStore().id(storeId).build();
        testContainer.storeRepository.save(store);

        // expect
        assertThatThrownBy(() -> testContainer.waitingValidator.validate(storeId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("브레이크 타임에는 웨이팅을 등록할 수 없습니다.");
    }
}
