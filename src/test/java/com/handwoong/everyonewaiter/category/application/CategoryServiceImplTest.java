package com.handwoong.everyonewaiter.category.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryServiceImplTest {

    private TestContainer testContainer;

    @BeforeEach
    void setUp() {
        final Username username = new Username("handwoong");
        testContainer = new TestContainer();
        testContainer.setSecurityContextAuthentication(username);

        final User user = aUser().build();
        testContainer.userRepository.save(user);

        final Store store = aStore().build();
        testContainer.storeRepository.save(store);
    }

    @Test
    void Should_Create_When_ValidCategoryCreate() {
        // given
        final CategoryCreate categoryCreate = CategoryCreate.builder()
            .storeId(new StoreId(1L))
            .name(new CategoryName("스테이크"))
            .icon("drumstick")
            .build();

        // when
        final CategoryId categoryId = testContainer.categoryService.create(categoryCreate);

        // then
        assertThat(categoryId).isEqualTo(new CategoryId(1L));
    }

    @Test
    void Should_ThrowException_When_CreateUserNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("username"));
        final CategoryCreate categoryCreate = CategoryCreate.builder()
            .storeId(new StoreId(1L))
            .name(new CategoryName("스테이크"))
            .icon("drumstick")
            .build();

        // expect
        assertThatThrownBy(() -> testContainer.categoryService.create(categoryCreate))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_ThrowException_When_CreateStoreNotFound() {
        // given
        final CategoryCreate categoryCreate = CategoryCreate.builder()
            .storeId(new StoreId(2L))
            .name(new CategoryName("스테이크"))
            .icon("drumstick")
            .build();

        // expect
        assertThatThrownBy(() -> testContainer.categoryService.create(categoryCreate))
            .isInstanceOf(StoreNotFoundException.class)
            .hasMessage("매장을 찾을 수 없습니다.");
    }
}
