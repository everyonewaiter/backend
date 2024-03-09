package com.handwoong.everyonewaiter.category.dto;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;

@Builder
public record CategoryDelete(CategoryId id, StoreId storeId) {

	public static CategoryDelete of(final Long id, final Long storeId) {
		return CategoryDelete.builder()
				.id(new CategoryId(id))
				.storeId(new StoreId(storeId))
				.build();
	}
}
