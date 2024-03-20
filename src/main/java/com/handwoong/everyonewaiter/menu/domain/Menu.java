package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Menu extends AggregateRoot {

	private final MenuId id;
	private final StoreId storeId;
	private final CategoryId categoryId;
	private final MenuName name;
	private final MenuDescription description;
	private final String image;
	private final Money price;
	private final MenuSpicy spicy;
	private final boolean printBillInKitchen;
	private final MenuStatus status;
	private final MenuLabel label;
	private final MenuOptionGroups optionGroups;

	public static Menu create(final MenuCreate menuCreate, final MenuValidator menuValidator) {
		menuValidator.validate(menuCreate.storeId(), menuCreate.categoryId());
		return Menu.builder()
				.storeId(menuCreate.storeId())
				.categoryId(menuCreate.categoryId())
				.name(menuCreate.name())
				.description(menuCreate.description())
				.image(menuCreate.image())
				.price(menuCreate.price())
				.spicy(menuCreate.spicy())
				.printBillInKitchen(menuCreate.printBillInKitchen())
				.status(menuCreate.status())
				.label(menuCreate.label())
				.optionGroups(menuCreate.optionGroups())
				.build();
	}

	public Menu update(final MenuUpdate menuUpdate, final MenuValidator menuValidator) {
		menuValidator.validate(menuUpdate.storeId(), menuUpdate.categoryId());
		return Menu.builder()
				.id(id)
				.storeId(storeId)
				.categoryId(categoryId)
				.name(menuUpdate.name())
				.description(menuUpdate.description())
				.image(menuUpdate.image())
				.price(menuUpdate.price())
				.spicy(menuUpdate.spicy())
				.printBillInKitchen(menuUpdate.printBillInKitchen())
				.status(menuUpdate.status())
				.label(menuUpdate.label())
				.optionGroups(menuUpdate.optionGroups())
				.build();
	}
}
