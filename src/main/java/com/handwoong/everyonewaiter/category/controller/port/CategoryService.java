package com.handwoong.everyonewaiter.category.controller.port;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;

public interface CategoryService {

    CategoryId create(CategoryCreate categoryCreate);
}
