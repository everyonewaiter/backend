package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.exception.InvalidStoreNameFormatException;
import org.springframework.util.StringUtils;

public record StoreName(String name) {

	public static final String STORE_NAME_EMPTY_MESSAGE = "매장 이름을 입력해주세요.";
	public static final String STORE_NAME_MAX_LENGTH_MESSAGE = "매장 이름은 50자 이하로 입력해주세요.";
	public static final int STORE_NAME_MAX_LENGTH = 50;

	public StoreName {
		validateNotEmpty(name);
		validateLength(name);
	}

	private void validateNotEmpty(final String name) {
		if (!StringUtils.hasText(name)) {
			throw new InvalidStoreNameFormatException(STORE_NAME_EMPTY_MESSAGE, name);
		}
	}

	private void validateLength(final String name) {
		if (name.length() > STORE_NAME_MAX_LENGTH) {
			throw new InvalidStoreNameFormatException(STORE_NAME_MAX_LENGTH_MESSAGE, name);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
