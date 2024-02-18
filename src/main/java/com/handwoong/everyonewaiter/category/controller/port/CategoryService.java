package com.handwoong.everyonewaiter.category.controller.port;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;

public interface CategoryService {

    CategoryId create(CategoryCreate categoryCreate);

    void update(CategoryUpdate categoryUpdate);
}
