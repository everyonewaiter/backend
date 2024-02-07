package com.handwoong.everyonewaiter.waiting.application.port;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import java.time.LocalDateTime;

public interface WaitingRepository {

    Waiting save(Waiting waiting);

    int countByAfterStoreOpen(StoreId storeId, WaitingStatus status, LocalDateTime lastOpenedAt);
}
