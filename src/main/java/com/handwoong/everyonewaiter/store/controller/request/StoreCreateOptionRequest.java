package com.handwoong.everyonewaiter.store.controller.request;

import com.handwoong.everyonewaiter.store.domain.StoreOption;
import jakarta.validation.constraints.NotNull;

public record StoreCreateOptionRequest(
    @NotNull
    Boolean useBreakTime,

    @NotNull
    Boolean useWaiting,

    @NotNull
    Boolean useOrder
) {

    public StoreOption toDomain() {
        return StoreOption.builder()
            .useBreakTime(useBreakTime)
            .useWaiting(useWaiting)
            .useOrder(useOrder)
            .build();
    }
}
