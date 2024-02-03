package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Store {

    private final StoreId id;
    private final UserId userId;
    private final StoreName name;
    private final LandlineNumber landlineNumber;
    private final StoreStatus status;
    private final LocalDateTime lastOpenedAt;
    private final LocalDateTime lastClosedAt;
    private final StoreBreakTimes breakTimes;
    private final StoreBusinessTimes businessTimes;
    private final StoreOption option;
    private final DomainTimestamp timestamp;
}
