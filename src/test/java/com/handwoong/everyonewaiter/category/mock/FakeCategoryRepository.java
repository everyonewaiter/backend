package com.handwoong.everyonewaiter.category.mock;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
