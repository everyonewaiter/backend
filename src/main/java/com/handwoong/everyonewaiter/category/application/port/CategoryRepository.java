package com.handwoong.everyonewaiter.category.application.port;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;

public interface CategoryRepository {

	Category save(Category category);

	Category findByIdOrElseThrow(CategoryId id);
}
