package com.handwoong.everyonewaiter.menu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuMultiSelectOption {

	private final MenuMultiSelectOptionId id;
	private final MenuOptionName name;
	private final Money price;
}
