package com.handwoong.everyonewaiter.menu.controller.request;

import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH;
import static com.handwoong.everyonewaiter.menu.domain.MenuOptionName.MENU_OPTION_NAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.menu.domain.MenuOptionName;
import com.handwoong.everyonewaiter.menu.domain.MenuSingleSelectOption;
import com.handwoong.everyonewaiter.menu.domain.Money;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MenuSingleSelectOptionRequest(
		@NotBlank(message = MENU_OPTION_NAME_EMPTY_MESSAGE)
		@Size(max = MENU_OPTION_NAME_MAX_LENGTH, message = MENU_OPTION_NAME_MAX_LENGTH_MESSAGE)
		String name,

		long price,

		boolean isDefault
) {

	public MenuSingleSelectOption toDomain() {
		return MenuSingleSelectOption.builder()
				.name(new MenuOptionName(name))
				.price(new Money(price))
				.isDefault(isDefault)
				.build();
	}
}
