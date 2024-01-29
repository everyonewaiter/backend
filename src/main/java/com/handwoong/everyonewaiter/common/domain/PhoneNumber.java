package com.handwoong.everyonewaiter.common.domain;

import com.handwoong.everyonewaiter.common.exception.InvalidPhoneNumberFormatException;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

public record PhoneNumber(String phoneNumber) {

    public static final String PHONE_NUMBER_REGEX = "^01[016789]\\d{7,8}$";

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    public PhoneNumber {
        validateNotEmpty(phoneNumber);
        validateFormat(phoneNumber);
    }

    private void validateNotEmpty(final String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new InvalidPhoneNumberFormatException("휴대폰 번호를 입력해주세요.", phoneNumber);
        }
    }

    private void validateFormat(final String phoneNumber) {
        if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberFormatException("휴대폰 번호의 형식이 옳바르지 않습니다.", phoneNumber);
        }
    }

    @Override
    public String toString() {
        return phoneNumber;
    }
}
