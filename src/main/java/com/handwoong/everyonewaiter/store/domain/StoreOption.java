package com.handwoong.everyonewaiter.store.domain;

import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreOption {

    private final StoreOptionId id;
    private final boolean useBreakTime;
    private final boolean useWaiting;
    private final boolean useOrder;

    public StoreOption update(final StoreOptionUpdate storeOptionUpdate) {
        return StoreOption.builder()
            .id(id)
            .useBreakTime(storeOptionUpdate.useBreakTime())
            .useWaiting(storeOptionUpdate.useWaiting())
            .useOrder(storeOptionUpdate.useOrder())
            .build();
    }
}
