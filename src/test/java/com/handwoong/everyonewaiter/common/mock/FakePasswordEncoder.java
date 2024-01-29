package com.handwoong.everyonewaiter.common.mock;

import org.springframework.security.crypto.password.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

    private final String fixture;

    public FakePasswordEncoder(final String fixture) {
        this.fixture = fixture;
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword + fixture;
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return rawPassword.equals(decode(encodedPassword));
    }

    private String decode(final String encodedPassword) {
        return encodedPassword.replace(fixture, "");
    }
}
