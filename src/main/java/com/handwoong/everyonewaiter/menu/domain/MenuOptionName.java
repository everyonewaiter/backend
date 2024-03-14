package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.exception.InvalidOptionNameFormatException;
import org.springframework.util.StringUtils;

public record MenuOptionName(String name) {

	public static final String MENU_OPTION_NAME_EMPTY_MESSAGE = "옵션 이름을 입력해주세요.";
	public static final String MENU_OPTION_NAME_MAX_LENGTH_MESSAGE = "옵션 이름은 30자 이하로 입력해주세요.";
	public static final int MENU_OPTION_NAME_MAX_LENGTH = 30;

	public MenuOptionName {
		validateNotEmpty(name);
		validateLength(name);
	}

	private void validateNotEmpty(final String name) {
		if (!StringUtils.hasText(name)) {
			throw new InvalidOptionNameFormatException(MENU_OPTION_NAME_EMPTY_MESSAGE, name);
		}
	}

	private void validateLength(final String name) {
		if (name.length() > MENU_OPTION_NAME_MAX_LENGTH) {
			throw new InvalidOptionNameFormatException(MENU_OPTION_NAME_MAX_LENGTH_MESSAGE, name);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
