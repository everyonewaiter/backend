package com.handwoong.everyonewaiter.menu.dto;

import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.menu.domain.MenuDescription;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuName;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroups;
import com.handwoong.everyonewaiter.menu.domain.MenuSpicy;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.domain.Money;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;

@Builder
public record MenuCreate(
		StoreId storeId,
		CategoryId categoryId,
		MenuName name,
		MenuDescription description,
		String image,
		Money price,
		MenuSpicy spicy,
		boolean printBillInKitchen,
		MenuStatus status,
		MenuLabel label,
		MenuOptionGroups optionGroups
) {

}
