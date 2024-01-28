package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.user.exception.InvalidUsernameFormatException;
import org.springframework.util.StringUtils;

public record Username(String username) {

    public Username {
        validateNotEmpty(username);
        validateNonBlank(username);
        validateLength(username);
    }

    private void validateNotEmpty(final String username) {
        if (!StringUtils.hasText(username)) {
            throw new InvalidUsernameFormatException("사용자 아이디를 입력해주세요.", username);
        }
    }

    private void validateNonBlank(final String username) {
        final int removeBlankUsernameLength = username.replaceAll("\\s", "").length();
        if (removeBlankUsernameLength != username.length()) {
            throw new InvalidUsernameFormatException("사용자 아이디는 공백이 포함될 수 없습니다.", username);
        }
    }

    private void validateLength(final String username) {
        if (username.length() > 30) {
            throw new InvalidUsernameFormatException("사용자 아이디는 30자 이하로 입력해주세요.", username);
        }
    }

    @Override
    public String toString() {
        return username;
    }
}
