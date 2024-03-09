package com.handwoong.everyonewaiter.category.controller.response;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import lombok.Builder;

@Builder
public record CategoryResponse(
		Long id,
		Long storeId,
		String name,
		String icon,
		DomainTimestamp timestamp
) {

	public static CategoryResponse from(final Category category) {
		return CategoryResponse.builder()
				.id(category.getId().value())
				.storeId(category.getStoreId().value())
				.name(category.getName().toString())
				.icon(category.getIcon().toString())
				.timestamp(category.getTimestamp())
				.build();
	}
}
