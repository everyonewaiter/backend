package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.exception.InvalidLandlineNumberFormatException;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public record LandlineNumber(String landlineNumber) {

	public static final String LANDLINE_NUMBER_EMPTY_MESSAGE = "매장 번호를 입력해주세요.";
	public static final String LANDLINE_NUMBER_FORMAT_MESSAGE = "매장 번호의 형식이 옳바르지 않습니다.";
	public static final String LANDLINE_NUMBER_REGEX = "^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]))(\\d{3,4})(\\d{4})$";

	private static final Pattern LANDLINE_NUMBER_PATTERN = Pattern.compile(LANDLINE_NUMBER_REGEX);

	public LandlineNumber {
		validateNotEmpty(landlineNumber);
		validateFormat(landlineNumber);
	}

	private void validateNotEmpty(final String landlineNumber) {
		if (!StringUtils.hasText(landlineNumber)) {
			throw new InvalidLandlineNumberFormatException(LANDLINE_NUMBER_EMPTY_MESSAGE, landlineNumber);
		}
	}

	private void validateFormat(final String landlineNumber) {
		if (!LANDLINE_NUMBER_PATTERN.matcher(landlineNumber).matches()) {
			throw new InvalidLandlineNumberFormatException(LANDLINE_NUMBER_FORMAT_MESSAGE, landlineNumber);
		}
	}

	@Override
	public String toString() {
		return landlineNumber;
	}
}
