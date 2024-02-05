package com.handwoong.everyonewaiter.store.dto;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import lombok.Builder;

@Builder
public record StoreUpdate(
    StoreId id,
    StoreName name,
    LandlineNumber landlineNumber,
    StoreBusinessTimes businessTimes,
    StoreBreakTimes breakTimes
) {

}
