package com.handwoong.everyonewaiter.category.application;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.controller.port.CategoryService;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryValidator;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    @Transactional
    public CategoryId create(final CategoryCreate categoryCreate) {
        final Category category = Category.create(categoryCreate, categoryValidator);
        final Category createdCategory = categoryRepository.save(category);
        return createdCategory.getId();
    }
}