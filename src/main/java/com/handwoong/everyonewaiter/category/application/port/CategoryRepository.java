package com.handwoong.everyonewaiter.category.application.port;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;

public interface CategoryRepository {

	Category save(Category category);

	Category findByIdOrElseThrow(CategoryId id);

	List<Category> findAllByStoreId(StoreId storeId);

	void delete(Category category);
}
