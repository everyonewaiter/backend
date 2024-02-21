package com.handwoong.everyonewaiter.category.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryIcon;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryName;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
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

		final Category category = aCategory().build();
		testContainer.categoryRepository.save(category);
	}

	@Test
	void Should_Create_When_ValidCategoryCreate() {
		// given
		final CategoryCreate categoryCreate = CategoryCreate.builder()
				.storeId(new StoreId(1L))
				.name(new CategoryName("스테이크"))
				.icon(new CategoryIcon("drumstick"))
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
				.icon(new CategoryIcon("drumstick"))
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
				.icon(new CategoryIcon("drumstick"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.create(categoryCreate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_Update_When_ValidCategoryUpdate() {
		// given
		final CategoryId categoryId = new CategoryId(1L);
		final CategoryName categoryName = new CategoryName("파스타");
		final CategoryIcon categoryIcon = new CategoryIcon("utensils");
		final CategoryUpdate categoryUpdate = CategoryUpdate.builder()
				.id(categoryId)
				.storeId(new StoreId(1L))
				.name(categoryName)
				.icon(categoryIcon)
				.build();

		// when
		testContainer.categoryService.update(categoryUpdate);
		final Category result = testContainer.categoryRepository.findByIdOrElseThrow(categoryId);

		// then
		assertThat(result.getName()).isEqualTo(categoryName);
		assertThat(result.getIcon()).isEqualTo(categoryIcon);
	}

	@Test
	void Should_ThrowException_When_UpdateUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final CategoryUpdate categoryUpdate = CategoryUpdate.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(2L))
				.name(new CategoryName("파스타"))
				.icon(new CategoryIcon("utensils"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.update(categoryUpdate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateStoreNotFound() {
		// given
		final CategoryUpdate categoryUpdate = CategoryUpdate.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(2L))
				.name(new CategoryName("파스타"))
				.icon(new CategoryIcon("utensils"))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.update(categoryUpdate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}
}
