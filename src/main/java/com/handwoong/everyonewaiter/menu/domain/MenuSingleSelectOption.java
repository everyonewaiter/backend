package com.handwoong.everyonewaiter.menu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuSingleSelectOption {

	private final MenuSingleSelectOptionId id;
	private final MenuOptionName name;
	private final Money price;
	private final boolean isDefault;
}
