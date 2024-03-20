package com.handwoong.everyonewaiter.menu.controller.request;

import static com.handwoong.everyonewaiter.menu.domain.MenuDescription.MENU_DESCRIPTION_MAX_LENGTH;
import static com.handwoong.everyonewaiter.menu.domain.MenuDescription.MENU_DESCRIPTION_MAX_LENGTH_MESSAGE;
import static com.handwoong.everyonewaiter.menu.domain.MenuName.MENU_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.menu.domain.MenuName.MENU_NAME_MAX_LENGTH;
import static com.handwoong.everyonewaiter.menu.domain.MenuName.MENU_NAME_MAX_LENGTH_MESSAGE;
import static com.handwoong.everyonewaiter.menu.domain.MenuSpicy.MAX_SPICY;
import static com.handwoong.everyonewaiter.menu.domain.MenuSpicy.MENU_SPICY_MAX_VALUE_MESSAGE;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.menu.domain.MenuDescription;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuName;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroups;
import com.handwoong.everyonewaiter.menu.domain.MenuSpicy;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.domain.Money;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record MenuUpdateRequest(
		@NotNull
		Long menuId,

		@NotNull
		Long storeId,

		@NotNull
		Long categoryId,

		@NotBlank(message = MENU_NAME_EMPTY_MESSAGE)
		@Size(max = MENU_NAME_MAX_LENGTH, message = MENU_NAME_MAX_LENGTH_MESSAGE)
		String name,

		@NotNull
		@Size(max = MENU_DESCRIPTION_MAX_LENGTH, message = MENU_DESCRIPTION_MAX_LENGTH_MESSAGE)
		String description,

		@NotNull
		String image,

		@NotNull
		long price,

		@NotNull
		@Max(value = MAX_SPICY, message = MENU_SPICY_MAX_VALUE_MESSAGE)
		int spicy,

		@NotNull
		boolean printBillInKitchen,

		@NotNull
		MenuStatus status,

		@NotNull
		MenuLabel label,

		@NotNull
		List<@Valid MenuOptionGroupRequest> optionGroups
) {

	public MenuUpdate toDomain() {
		return MenuUpdate.builder()
				.menuId(new MenuId(menuId))
				.storeId(new StoreId(storeId))
				.categoryId(new CategoryId(categoryId))
				.name(new MenuName(name))
				.description(new MenuDescription(description))
				.image(image)
				.price(new Money(price))
				.spicy(new MenuSpicy(spicy))
				.printBillInKitchen(printBillInKitchen)
				.status(status)
				.label(label)
				.optionGroups(
						new MenuOptionGroups(
								optionGroups.stream()
										.map(MenuOptionGroupRequest::toDomain)
										.toList()
						)
				)
				.build();
	}
}
