package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.category.application.port.CategoryRepository;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.store.application.port.StoreRepository;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.application.port.UserRepository;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuValidator {

	private final UserRepository userRepository;
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;

	public void validate(final StoreId storeId, final CategoryId categoryId) {
		final Store store = getStore(storeId, getUser());
		validateCategory(categoryId, store);
	}

	private User getUser() {
		final Username username = SecurityUtils.getAuthenticationUsername();
		return userRepository.findByUsernameOrElseThrow(username);
	}

	private Store getStore(final StoreId storeId, final User user) {
		return storeRepository.findByIdAndUserIdOrElseThrow(storeId, user.getId());
	}

	private void validateCategory(final CategoryId categoryId, final Store store) {
		if (!categoryRepository.existsByIdAndStoreId(categoryId, store.getId())) {
			throw new CategoryNotFoundException(
					"카테고리를 찾을 수 없습니다.", "id : [%s] storeId : [%s]".formatted(categoryId, store.getId()));
		}
	}
}
