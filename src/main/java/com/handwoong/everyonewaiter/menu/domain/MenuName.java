package com.handwoong.everyonewaiter.menu.domain;

import com.handwoong.everyonewaiter.menu.exception.InvalidMenuNameFormatException;
import org.springframework.util.StringUtils;

public record MenuName(String name) {

	public static final String MENU_NAME_EMPTY_MESSAGE = "메뉴 이름을 입력해주세요.";
	public static final String MENU_NAME_MAX_LENGTH_MESSAGE = "메뉴 이름은 20자 이하로 입력해주세요.";
	public static final int MENU_NAME_MAX_LENGTH = 20;

	public MenuName {
		validateNotEmpty(name);
		validateLength(name);
	}

	private void validateNotEmpty(final String name) {
		if (!StringUtils.hasText(name)) {
			throw new InvalidMenuNameFormatException(MENU_NAME_EMPTY_MESSAGE, name);
		}
	}

	private void validateLength(final String name) {
		if (name.length() > MENU_NAME_MAX_LENGTH) {
			throw new InvalidMenuNameFormatException(MENU_NAME_MAX_LENGTH_MESSAGE, name);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
