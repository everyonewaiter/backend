package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
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
}
