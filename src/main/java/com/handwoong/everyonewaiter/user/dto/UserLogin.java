package com.handwoong.everyonewaiter.user.dto;

import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.Username;
import lombok.Builder;

@Builder
public record UserLogin(Username username, Password password) {

}
