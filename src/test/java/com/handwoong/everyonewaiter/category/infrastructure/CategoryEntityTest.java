package com.handwoong.everyonewaiter.category.infrastructure;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.Category;
import org.junit.jupiter.api.Test;

class CategoryEntityTest {

	@Test
	void Should_CreateEntity_When_FromModel() {
		// given
		final Category category = aCategory().build();

		// when
		final CategoryEntity categoryEntity = CategoryEntity.from(category);

		// then
		assertThat(categoryEntity).extracting("id").isEqualTo(1L);
	}

	@Test
	void Should_CreateDomain_When_ToModel() {
		// given
		final Category category = aCategory().build();
		final CategoryEntity categoryEntity = CategoryEntity.from(category);

		// when
		final Category result = categoryEntity.toModel();

		// then
		assertThat(result.getId().value()).isEqualTo(1L);
	}

	@Test
	void Should_ThrowException_When_StoreIdIsNull() {
		// given
		final Category category = aCategory().storeId(null).build();

		// expect
		assertThatThrownBy(() -> CategoryEntity.from(category))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("카테고리의 매장 ID는 null일 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_FromModelCategoryNameIsNull() {
		// given
		final Category category = aCategory().name(null).build();

		// expect
		assertThatThrownBy(() -> CategoryEntity.from(category))
				.isInstanceOf(NullPointerException.class);
	}
}
