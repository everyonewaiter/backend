package com.handwoong.everyonewaiter.user.domain;

import com.handwoong.everyonewaiter.user.exception.InvalidPasswordFormatException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public record Password(String password) {

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
