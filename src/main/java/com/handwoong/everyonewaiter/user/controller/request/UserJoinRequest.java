package com.handwoong.everyonewaiter.user.controller.request;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.dto.UserJoin;
import jakarta.validation.constraints.Pattern;

public record UserJoinRequest(
    String username,

    @Pattern(regexp = "^\\d{6}$", message = "비밀번호는 6자리 숫자로 입력해주세요.")
    String password,

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
