package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.user.exception.InvalidUsernameFormatException;
import org.springframework.util.StringUtils;

public record Username(String username) {

    public static final String USERNAME_EMPTY_MESSAGE = "사용자 아이디를 입력해주세요.";
    public static final String USERNAME_INCLUDE_BLANK_MESSAGE = "사용자 아이디는 공백이 포함될 수 없습니다.";
    public static final String USERNAME_MAX_LENGTH_MESSAGE = "사용자 아이디는 30자 이하로 입력해주세요.";
    public static final int USERNAME_MAX_LENGTH = 30;

    public Username {
        validateNotEmpty(username);
        validateNonBlank(username);
        validateLength(username);
    }

    private void validateNotEmpty(final String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidUsernameFormatException(USERNAME_EMPTY_MESSAGE, username);
        }
    }

    private void validateNonBlank(final String username) {
        final int removeBlankUsernameLength = username.replaceAll("\\s", "").length();
        if (removeBlankUsernameLength != username.length()) {
            throw new InvalidUsernameFormatException(USERNAME_INCLUDE_BLANK_MESSAGE, username);
        }
    }

    private void validateLength(final String username) {
        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new InvalidUsernameFormatException(USERNAME_MAX_LENGTH_MESSAGE, username);
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
