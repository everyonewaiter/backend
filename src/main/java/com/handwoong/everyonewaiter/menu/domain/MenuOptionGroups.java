package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.infrastructure.MenuOptionGroupEntity;
import java.util.List;

public record MenuOptionGroups(List<MenuOptionGroup> optionGroups) {

	public List<MenuOptionGroupEntity> toEntity() {
		return optionGroups.stream()
				.map(MenuOptionGroupEntity::from)
				.toList();
	}
}
