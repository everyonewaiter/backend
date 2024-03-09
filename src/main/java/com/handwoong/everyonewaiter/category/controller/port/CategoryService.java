package com.handwoong.everyonewaiter.category.controller.port;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;

public interface CategoryService {

	CategoryId create(CategoryCreate categoryCreate);

	void update(CategoryUpdate categoryUpdate);

	List<Category> findAllByStoreId(StoreId storeId);
}
