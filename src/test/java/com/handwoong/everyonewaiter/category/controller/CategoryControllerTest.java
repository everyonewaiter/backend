package com.handwoong.everyonewaiter.category.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.controller.request.CategoryCreateRequest;
import com.handwoong.everyonewaiter.category.controller.request.CategoryUpdateRequest;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CategoryControllerTest {

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

        final Category category = aCategory().build();
        testContainer.categoryRepository.save(category);
    }

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크", "drumstick");

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.categoryController.create(request);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
    }

    @Test
    void Should_Status400_When_CreateUserNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크", "drumstick");

        // expect
        assertThatThrownBy(() -> testContainer.categoryController.create(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_Status400_When_CreateStoreNotFound() {
        // given
        final CategoryCreateRequest request = new CategoryCreateRequest(2L, "스테이크", "drumstick");

        // expect
        assertThatThrownBy(() -> testContainer.categoryController.create(request))
            .isInstanceOf(StoreNotFoundException.class)
            .hasMessage("매장을 찾을 수 없습니다.");
    }

    @Test
    void Should_Update_When_ValidRequest() {
        // given
        final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 1L, "파스타", "utensils");

        // when
        final ResponseEntity<ApiResponse<Void>> response = testContainer.categoryController.update(request);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void Should_Status400_When_UpdateCategoryNotFound() {
        // given
        final CategoryUpdateRequest request = new CategoryUpdateRequest(2L, 1L, "파스타", "utensils");

        // expect
        assertThatThrownBy(() -> testContainer.categoryController.update(request))
            .isInstanceOf(CategoryNotFoundException.class)
            .hasMessage("카테고리를 찾을 수 없습니다.");
    }

    @Test
    void Should_Status400_When_UpdateUserNotFound() {
        // given
        testContainer.setSecurityContextAuthentication(new Username("notfound"));
        final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 1L, "파스타", "utensils");

        // expect
        assertThatThrownBy(() -> testContainer.categoryController.update(request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void Should_Status400_When_UpdateStoreNotFound() {
        // given
        final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, "파스타", "utensils");

        // expect
        assertThatThrownBy(() -> testContainer.categoryController.update(request))
            .isInstanceOf(StoreNotFoundException.class)
            .hasMessage("매장을 찾을 수 없습니다.");
    }
}