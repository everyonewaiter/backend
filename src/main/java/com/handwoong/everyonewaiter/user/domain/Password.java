package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.user.exception.InvalidPasswordFormatException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public record Password(String password) {

    public static final String PASSWORD_FORMAT_MESSAGE = "비밀번호는 6자리 숫자로 입력해주세요.";
    public static final String PASSWORD_REGEX = "^\\d{6}$";

    public Password {
        validateNotEmpty(password);
    }

    private void validateNotEmpty(final String password) {
        if (!StringUtils.hasText(password)) {
            throw new InvalidPasswordFormatException();
        }
    }

    public Password encode(final PasswordEncoder passwordEncoder) {
        final String encodedPassword = passwordEncoder.encode(password);
        return new Password(encodedPassword);
    }

    @Override
    public String toString() {
        return password;
    }
}
