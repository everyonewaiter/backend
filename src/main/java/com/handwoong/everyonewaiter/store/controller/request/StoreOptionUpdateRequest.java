package com.handwoong.everyonewaiter.store.controller.request;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import jakarta.validation.constraints.NotNull;

public record StoreOptionUpdateRequest(
    @NotNull
    Long storeId,

    @NotNull
    Boolean useBreakTime,

    @NotNull
    Boolean useWaiting,

    @NotNull
    Boolean useOrder
) {

    public StoreOptionUpdate toDomainDto() {
        return StoreOptionUpdate.builder()
            .storeId(new StoreId(storeId))
            .useBreakTime(useBreakTime)
            .useWaiting(useWaiting)
            .useOrder(useOrder)
            .build();
    }
}
