package com.handwoong.everyonewaiter.waiting.domain.event;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;

public record WaitingCancelEvent(StoreId storeId, WaitingNotificationType notificationType, PhoneNumber phoneNumber) {

}
