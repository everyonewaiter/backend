package com.handwoong.everyonewaiter.user.controller.request;

import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_REGEX;
import static com.handwoong.everyonewaiter.user.domain.Password.PASSWORD_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.user.domain.Password.PASSWORD_REGEX;
import static com.handwoong.everyonewaiter.user.domain.Username.MAX_LENGTH;
import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserJoinRequest(
    @NotBlank(message = USERNAME_EMPTY_MESSAGE)
    @Size(max = MAX_LENGTH, message = USERNAME_MAX_LENGTH_MESSAGE)
    String username,

    @NotBlank(message = PASSWORD_FORMAT_MESSAGE)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_FORMAT_MESSAGE)
    String password,

    @NotBlank(message = PHONE_NUMBER_EMPTY_MESSAGE)
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_FORMAT_MESSAGE)
    String phoneNumber
) {

    public UserJoin toDomainDto() {
        return UserJoin.builder()
            .username(new Username(username))
            .password(new Password(password))
            .phoneNumber(new PhoneNumber(phoneNumber))
            .build();
    }
}
