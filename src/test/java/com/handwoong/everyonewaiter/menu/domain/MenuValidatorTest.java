package com.handwoong.everyonewaiter.menu.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuValidatorTest {

	private TestContainer testContainer;

	@BeforeEach
	void setUp() {
		final Username username = new Username("handwoong");
		testContainer = new TestContainer();
		testContainer.setSecurityContextAuthentication(username);
		testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final Store store = aStore().build();
		testContainer.storeRepository.save(store);

		final Category category = aCategory().build();
		testContainer.categoryRepository.save(category);
	}

	@Test
	void Should_DoesNotThrowException_When_Validate() {
		// given
		final StoreId storeId = new StoreId(1L);
		final CategoryId categoryId = new CategoryId(1L);

		// expect
		assertThatCode(() -> testContainer.menuValidator.validate(storeId, categoryId))
				.doesNotThrowAnyException();
	}

	@Test
	void Should_ThrowException_When_UserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final StoreId storeId = new StoreId(1L);
		final CategoryId categoryId = new CategoryId(1L);

		// expect
		assertThatThrownBy(() -> testContainer.menuValidator.validate(storeId, categoryId))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_StoreNotFound() {
		// given
		final StoreId storeId = new StoreId(2L);
		final CategoryId categoryId = new CategoryId(1L);

		// expect
		assertThatThrownBy(() -> testContainer.menuValidator.validate(storeId, categoryId))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CategoryNotFound() {
		// given
		final StoreId storeId = new StoreId(1L);
		final CategoryId categoryId = new CategoryId(2L);

		// expect
		assertThatThrownBy(() -> testContainer.menuValidator.validate(storeId, categoryId))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}
}
