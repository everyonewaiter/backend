package com.handwoong.everyonewaiter.menu.controller.response;

import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroup;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuOptionGroupResponse(
		Long id,
		String name,
		boolean useOptionPrice,
		List<MenuSingleSelectOptionResponse> singleSelectOptions,
		List<MenuMultiSelectOptionResponse> multiSelectOptions
) {

	public static MenuOptionGroupResponse from(final MenuOptionGroup menuOptionGroup) {
		return MenuOptionGroupResponse.builder()
				.id(menuOptionGroup.getId().value())
				.name(menuOptionGroup.getName().toString())
				.useOptionPrice(menuOptionGroup.isUseOptionPrice())
				.singleSelectOptions(menuOptionGroup.getSingleSelectOptions()
						.options()
						.stream()
						.map(MenuSingleSelectOptionResponse::from)
						.toList()
				)
				.multiSelectOptions(menuOptionGroup.getMultiSelectOptions()
						.options()
						.stream()
						.map(MenuMultiSelectOptionResponse::from)
						.toList()
				)
				.build();
	}
}
