package com.handwoong.everyonewaiter.menu.controller.response;

import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOption;
import lombok.Builder;

@Builder
public record MenuMultiSelectOptionResponse(
		Long id,
		String name,
		long price
) {

	public static MenuMultiSelectOptionResponse from(final MenuMultiSelectOption menuMultiSelectOption) {
		return MenuMultiSelectOptionResponse.builder()
				.id(menuMultiSelectOption.getId().value())
				.name(menuMultiSelectOption.getName().toString())
				.price(menuMultiSelectOption.getPrice().value())
				.build();
	}
}
