package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.infrastructure.MenuMultiSelectOptionEntity;
import java.util.List;

public record MenuMultiSelectOptions(List<MenuMultiSelectOption> options) {

	public List<MenuMultiSelectOptionEntity> toEntity() {
		return options.stream()
				.map(MenuMultiSelectOptionEntity::from)
				.toList();
	}
}
