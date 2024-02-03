package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreOption {

    private final StoreOptionId id;
    private final boolean useBusinessTime;
    private final boolean useBreakTime;
    private final boolean useWaiting;
    private final boolean useOrder;
    private final DomainTimestamp timestamp;
}
