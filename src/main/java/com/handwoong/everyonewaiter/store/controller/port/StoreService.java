package com.handwoong.everyonewaiter.store.controller.port;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.user.domain.Username;

public interface StoreService {

    StoreId create(Username username, StoreCreate storeCreate);
}
