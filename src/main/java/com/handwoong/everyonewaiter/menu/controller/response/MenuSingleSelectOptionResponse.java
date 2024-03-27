package com.handwoong.everyonewaiter.menu.controller.response;

import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOption;
import lombok.Builder;

@Builder
public record MenuSingleSelectOptionResponse(
		Long id,
		String name,
		long price,
		boolean isDefault
) {

	public static MenuSingleSelectOptionResponse from(final MenuSingleSelectOption menuSingleSelectOption) {
		return MenuSingleSelectOptionResponse.builder()
				.id(menuSingleSelectOption.getId().value())
				.name(menuSingleSelectOption.getName().toString())
				.price(menuSingleSelectOption.getPrice().value())
				.isDefault(menuSingleSelectOption.isDefault())
				.build();
	}
}
