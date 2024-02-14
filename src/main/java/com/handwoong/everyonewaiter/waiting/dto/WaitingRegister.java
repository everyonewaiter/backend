package com.handwoong.everyonewaiter.waiting.dto;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import lombok.Builder;

@Builder
public record WaitingRegister(StoreId storeId, WaitingAdult adult, WaitingChildren children, PhoneNumber phoneNumber) {

}
