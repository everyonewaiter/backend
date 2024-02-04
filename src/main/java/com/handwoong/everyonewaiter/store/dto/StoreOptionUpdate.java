package com.handwoong.everyonewaiter.store.dto;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.Builder;

@Builder
public record StoreOptionUpdate(StoreId storeId, boolean useBreakTime, boolean useWaiting, boolean useOrder) {

}
