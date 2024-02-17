package com.handwoong.everyonewaiter.category.application.port;

import com.handwoong.everyonewaiter.category.domain.Category;

public interface CategoryRepository {

    Category save(Category category);
}
