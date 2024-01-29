package com.handwoong.everyonewaiter.user.dto;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.Username;
import lombok.Builder;

@Builder
public record UserJoin(Username username, Password password, PhoneNumber phoneNumber) {

}
