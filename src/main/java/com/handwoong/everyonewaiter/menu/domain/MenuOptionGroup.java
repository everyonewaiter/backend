package com.handwoong.everyonewaiter.menu.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuOptionGroup {

	private final MenuOptionGroupId id;
	private final MenuOptionName name;
	private final boolean useOptionPrice;
	private final MenuSingleSelectOptions singleSelectOptions;
	private final MenuMultiSelectOptions multiSelectOptions;
}
