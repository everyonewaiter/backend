package com.handwoong.everyonewaiter.waiting.dto;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.UUID;
import lombok.Builder;

@Builder
public record WaitingCancel(StoreId storeId, UUID uniqueCode) {

}
