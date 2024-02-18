package com.handwoong.everyonewaiter.category.infrastructure;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(final Category category) {
        return categoryJpaRepository.save(CategoryEntity.from(category)).toModel();
    }

    @Override
    public Category findByIdOrElseThrow(final CategoryId id) {
        return categoryJpaRepository.findById(id.value())
            .orElseThrow(() -> new CategoryNotFoundException("카테고리를 찾을 수 없습니다.", "id : [" + id + "]"))
            .toModel();
    }
}
