package com.handwoong.everyonewaiter.store.application.port;

import com.handwoong.everyonewaiter.store.domain.Store;

public interface StoreRepository {

    Store save(Store store);
}
