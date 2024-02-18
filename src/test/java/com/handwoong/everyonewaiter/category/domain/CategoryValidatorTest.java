package com.handwoong.everyonewaiter.category.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryValidatorTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        testContainer = new TestContainer();
        testContainer.setSecurityContextAuthentication(new Username("handwoong"));
    }

    @Test
    void Should_DoesNotThrowException_When_Validate() {
        // given
        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().build();
        testContainer.storeRepository.save(store);

        final StoreId storeId = new StoreId(1L);
        final CategoryValidator categoryValidator = testContainer.categoryValidator;

        // expect
        assertThatCode(() -> categoryValidator.validate(storeId)).doesNotThrowAnyException();
    }

    @Test
    void Should_ThrowException_When_UserNotFound() {
        // given
        final StoreId storeId = new StoreId(1L);
        final CategoryValidator categoryValidator = testContainer.categoryValidator;

        // expect
        assertThatThrownBy(() -> categoryValidator.validate(storeId))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_ThrowException_When_StoreNotFound() {
        // given
        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final StoreId storeId = new StoreId(1L);
        final CategoryValidator categoryValidator = testContainer.categoryValidator;

        // expect
        assertThatThrownBy(() -> categoryValidator.validate(storeId))
            .isInstanceOf(StoreNotFoundException.class)
            .hasMessage("매장을 찾을 수 없습니다.");
    }
}
