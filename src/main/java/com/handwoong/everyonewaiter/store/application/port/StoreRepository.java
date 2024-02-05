package com.handwoong.everyonewaiter.store.application.port;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.UserId;

public interface StoreRepository {

    Store save(Store store);

    Store findByIdAndUserIdOrElseThrow(StoreId storeId, UserId userId);

    void delete(Store store);
}
