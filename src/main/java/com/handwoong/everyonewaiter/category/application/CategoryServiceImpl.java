package com.handwoong.everyonewaiter.category.application;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.controller.port.CategoryService;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.domain.CategoryValidator;
import com.handwoong.everyonewaiter.category.dto.CategoryCreate;
import com.handwoong.everyonewaiter.category.dto.CategoryDelete;
import com.handwoong.everyonewaiter.category.dto.CategoryUpdate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;
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

	@Override
	@Transactional
	public void update(final CategoryUpdate categoryUpdate) {
		final Category category = categoryRepository.findByIdOrElseThrow(categoryUpdate.id());
		final Category updatedCategory = category.update(categoryUpdate, categoryValidator);
		categoryRepository.save(updatedCategory);
	}

	@Override
	public List<Category> findAllByStoreId(final StoreId storeId) {
		return categoryRepository.findAllByStoreId(storeId);
	}

	@Override
	@Transactional
	public void delete(final CategoryDelete categoryDelete) {
		final Category category = categoryRepository.findByIdOrElseThrow(categoryDelete.id());
		category.delete(categoryDelete, categoryValidator);
		categoryRepository.delete(category);
	}
}
