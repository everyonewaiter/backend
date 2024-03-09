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
import com.handwoong.everyonewaiter.category.dto.CategoryDelete;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.util.List;
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

	@Test
	void Should_TwoCategories_When_FindAllByStoreId() {
		// given
		final Category category = aCategory().id(new CategoryId(2L)).build();
		testContainer.categoryRepository.save(category);

		// when
		final List<Category> categories = testContainer.categoryService.findAllByStoreId(new StoreId(1L));

		// then
		assertThat(categories).hasSize(2);
	}

	@Test
	void Should_Zero_When_FindAllByStoreId() {
		// given
		// when
		final List<Category> categories = testContainer.categoryService.findAllByStoreId(new StoreId(10L));

		// then
		assertThat(categories).isEmpty();
	}

	@Test
	void Should_Delete_When_ValidCategoryDelete() {
		// given
		final StoreId storeId = new StoreId(1L);
		final CategoryDelete categoryDelete = CategoryDelete.builder()
				.id(new CategoryId(1L))
				.storeId(storeId)
				.build();

		// when
		testContainer.categoryService.delete(categoryDelete);
		final List<Category> result = testContainer.categoryRepository.findAllByStoreId(storeId);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void Should_ThrowException_When_DeleteUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final CategoryDelete categoryDelete = CategoryDelete.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.delete(categoryDelete))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_DeleteStoreNotFound() {
		// given
		final CategoryDelete categoryDelete = CategoryDelete.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(10L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.delete(categoryDelete))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_DeleteCategoryNotFound() {
		// given
		final CategoryDelete categoryDelete = CategoryDelete.builder()
				.id(new CategoryId(10L))
				.storeId(new StoreId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.categoryService.delete(categoryDelete))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}
}
