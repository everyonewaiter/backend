package com.handwoong.everyonewaiter.category.mock;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.HashMap;
import java.util.List;
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

	@Override
	public List<Category> findAllByStoreId(final StoreId storeId) {
		return database.values()
				.stream()
				.filter(category -> category.getStoreId().equals(storeId))
				.toList();
	}

	@Override
	public void delete(final Category category) {
		database.remove(category.getId().value());
	}

	@Override
	public boolean existsByIdAndStoreId(final CategoryId id, final StoreId storeId) {
		final long count = database.values()
				.stream()
				.filter(category -> category.getId().equals(id))
				.filter(category -> category.getStoreId().equals(storeId))
				.count();
		return count != 0;
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
