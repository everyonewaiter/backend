package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.infrastructure.MenuSingleSelectOptionEntity;
import java.util.List;

public record MenuSingleSelectOptions(List<MenuSingleSelectOption> options) {

	public List<MenuSingleSelectOptionEntity> toEntity() {
		return options.stream()
				.map(MenuSingleSelectOptionEntity::from)
				.toList();
	}
}
