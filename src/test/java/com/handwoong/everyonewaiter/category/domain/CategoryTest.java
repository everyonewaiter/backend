package com.handwoong.everyonewaiter.category.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryDelete;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

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
		final StoreId storeId = new StoreId(1L);
		final CategoryName categoryName = new CategoryName("스테이크");
		final CategoryIcon icon = new CategoryIcon("drumstick");

		final CategoryCreate categoryCreate = CategoryCreate.builder()
				.storeId(storeId)
				.name(categoryName)
				.icon(icon)
				.build();

		// when
		final Category category = Category.create(categoryCreate, testContainer.categoryValidator);

		// then
		assertThat(category.getStoreId()).isEqualTo(storeId);
		assertThat(category.getName()).isEqualTo(categoryName);
		assertThat(category.getIcon()).isEqualTo(icon);
	}

	@Test
	void Should_Update_When_ValidCategoryUpdate() {
		// given
		final Category category = aCategory().build();

		final CategoryName categoryName = new CategoryName("파스타");
		final CategoryIcon categoryIcon = new CategoryIcon("utensils");
		final CategoryUpdate categoryUpdate = CategoryUpdate.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(1L))
				.name(categoryName)
				.icon(categoryIcon)
				.build();

		// when
		final Category result = category.update(categoryUpdate, testContainer.categoryValidator);

		// then
		assertThat(result.getName()).isEqualTo(categoryName);
		assertThat(result.getIcon()).isEqualTo(categoryIcon);
	}

	@Test
	void Should_Validate_When_Delete() {
		// given
		final CategoryDelete categoryDelete = CategoryDelete.builder()
				.id(new CategoryId(1L))
				.storeId(new StoreId(1L))
				.build();
		final Category category = aCategory().build();

		// expect
		assertThatCode(() -> category.delete(categoryDelete, testContainer.categoryValidator))
				.doesNotThrowAnyException();
	}
}
