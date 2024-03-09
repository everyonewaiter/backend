package com.handwoong.everyonewaiter.category.controller.response;

import com.handwoong.everyonewaiter.category.domain.Category;
import java.util.List;
import lombok.Builder;

@Builder
public record CategoryResponses(List<CategoryResponse> categories) {

	public static CategoryResponses from(final List<Category> categories) {
		return CategoryResponses.builder()
				.categories(categories.stream().map(CategoryResponse::from).toList())
				.build();
	}
}
