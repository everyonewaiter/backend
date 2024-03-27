package com.handwoong.everyonewaiter.menu.controller.response;

import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuResponse(
		Long id,
		Long storeId,
		Long categoryId,
		String name,
		String description,
		String image,
		long price,
		int spicy,
		boolean printBillInKitchen,
		MenuStatus status,
		MenuLabel label,
		List<MenuOptionGroupResponse> optionGroups
) {

	public static MenuResponse from(final Menu menu) {
		return MenuResponse.builder()
				.id(menu.getId().value())
				.storeId(menu.getStoreId().value())
				.categoryId(menu.getCategoryId().value())
				.name(menu.getName().toString())
				.description(menu.getDescription().toString())
				.image(menu.getImage())
				.price(menu.getPrice().value())
				.spicy(menu.getSpicy().value())
				.printBillInKitchen(menu.isPrintBillInKitchen())
				.status(menu.getStatus())
				.label(menu.getLabel())
				.optionGroups(menu.getOptionGroups()
						.optionGroups()
						.stream()
						.map(MenuOptionGroupResponse::from)
						.toList()
				)
				.build();
	}
}
