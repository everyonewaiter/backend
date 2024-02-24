package com.handwoong.everyonewaiter.category.mock;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeCategoryRepository implements CategoryRepository {

	private final Map<Long, Category> database = new HashMap<>();

	private Long sequence = 1L;

	@Override
	public Category save(final Category category) {
		final Long id = Objects.nonNull(category.getId()) ? category.getId().value() : sequence++;
		final Category newCategory = create(id, category);
		database.put(id, newCategory);
		return newCategory;
	}

	@Override
	public Category findByIdOrElseThrow(final CategoryId id) {
		return Optional.ofNullable(database.get(id.value()))
				.orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다.", "id : [" + id + "]"));
	}

	private Category create(final Long id, final Category category) {
		return Category.builder()
				.id(new CategoryId(id))
				.storeId(category.getStoreId())
				.name(category.getName())
				.icon(category.getIcon())
				.timestamp(category.getTimestamp())
				.build();
	}
}
