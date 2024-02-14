package com.handwoong.everyonewaiter.waiting.domain.event;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.waiting.dto.WaitingGenerateInfo;

public record WaitingRegisterEvent(WaitingGenerateInfo waitingGenerateInfo, PhoneNumber phoneNumber) {

}
