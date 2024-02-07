package com.handwoong.everyonewaiter.waiting.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Waiting {

    private final WaitingId id;
    private final StoreId storeId;
    private final WaitingAdult adult;
    private final WaitingChildren children;
    private final WaitingNumber number;
    private final PhoneNumber phoneNumber;
    private final WaitingStatus status;
    private final WaitingNotificationType notificationType;
    private final UUID uniqueCode;
    private final DomainTimestamp timestamp;
}
