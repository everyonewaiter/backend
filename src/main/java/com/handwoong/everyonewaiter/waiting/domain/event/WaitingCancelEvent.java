package com.handwoong.everyonewaiter.waiting.domain.event;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;

public record WaitingCancelEvent(StoreId storeId, WaitingNumber number, PhoneNumber phoneNumber) {

}
