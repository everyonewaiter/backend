package com.handwoong.everyonewaiter.menu.controller.request;

import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH;
import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.menu.domain.MenuMultiSelectOptions;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroup;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOptions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record MenuOptionGroupRequest(
		@NotBlank(message = MENU_OPTION_NAME_EMPTY_MESSAGE)
		@Size(max = MENU_OPTION_NAME_MAX_LENGTH, message = MENU_OPTION_NAME_MAX_LENGTH_MESSAGE)
		String name,

		boolean useOptionPrice,

		@NotNull
		List<@Valid MenuSingleSelectOptionRequest> singleSelectOptions,

		@NotNull
		List<@Valid MenuMultiSelectOptionRequest> multiSelectOptions
) {

	public MenuOptionGroup toDomain() {
		return MenuOptionGroup.builder()
				.name(new MenuOptionName(name))
				.useOptionPrice(useOptionPrice)
				.singleSelectOptions(
						new MenuSingleSelectOptions(
								singleSelectOptions.stream()
										.map(MenuSingleSelectOptionRequest::toDomain)
										.toList()
						)
				)
				.multiSelectOptions(
						new MenuMultiSelectOptions(
								multiSelectOptions.stream()
										.map(MenuMultiSelectOptionRequest::toDomain)
										.toList()
						)
				)
				.build();
	}
}
