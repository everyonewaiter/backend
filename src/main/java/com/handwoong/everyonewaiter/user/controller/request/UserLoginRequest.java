package com.handwoong.everyonewaiter.user.controller.request;

import static com.handwoong.everyonewaiter.user.domain.Password.PASSWORD_REGEX;
import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserLogin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(
    @NotBlank(message = LOGIN_MESSAGE)
    @Size(max = USERNAME_MAX_LENGTH, message = LOGIN_MESSAGE)
    String username,

    @NotBlank(message = LOGIN_MESSAGE)
    @Pattern(regexp = PASSWORD_REGEX, message = LOGIN_MESSAGE)
    String password
) {

    public static final String LOGIN_MESSAGE = "아이디 및 비밀번호를 확인 해주세요.";

    public UserLogin toDomainDto() {
        return UserLogin.builder()
            .username(new Username(username))
            .password(new Password(password))
            .build();
    }
}
