package com.handwoong.everyonewaiter.category.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.controller.request.CategoryCreateRequest;
import com.handwoong.everyonewaiter.category.controller.request.CategoryUpdateRequest;
import com.handwoong.everyonewaiter.category.controller.response.CategoryResponses;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.util.Objects;
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
	void Should_TwoCategories_When_FindStoreCategories() {
		// given
		final Category category = aCategory().id(new CategoryId(2L)).build();
		testContainer.categoryRepository.save(category);

		// when
		final ResponseEntity<ApiResponse<CategoryResponses>> response = testContainer.categoryController.categories(1L);
		final CategoryResponses result = Objects.requireNonNull(response.getBody()).data();

		// then
		assertThat(result.categories()).hasSize(2);
	}

	@Test
	void Should_Zero_When_FindStoreCategories() {
		// given
		// when
		final ResponseEntity<ApiResponse<CategoryResponses>> response = testContainer.categoryController.categories(5L);
		final CategoryResponses result = Objects.requireNonNull(response.getBody()).data();

		// then
		assertThat(result.categories()).isEmpty();
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

	@Test
	void Should_Status200_When_Delete() {
		// given
		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.categoryController.delete(1L, 1L);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void Should_Status400_When_DeleteUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("notfound"));

		// expect
		assertThatThrownBy(() -> testContainer.categoryController.delete(1L, 1L))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_Status400_When_DeleteStoreNotFound() {
		// expect
		assertThatThrownBy(() -> testContainer.categoryController.delete(1L, 10L))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_Status400_When_DeleteCategoryNotFound() {
		// expect
		assertThatThrownBy(() -> testContainer.categoryController.delete(10L, 1L))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}
}
