package com.handwoong.everyonewaiter.store.controller.port;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import com.handwoong.everyonewaiter.store.dto.StoreOptionUpdate;
import com.handwoong.everyonewaiter.store.dto.StoreUpdate;
import com.handwoong.everyonewaiter.user.domain.Username;
import java.util.List;

public interface StoreService {

	List<Store> findAllByUsername(Username username);

	Store findByIdAndUsername(StoreId storeId, Username username);

	StoreId create(Username username, StoreCreate storeCreate);

	void update(Username username, StoreUpdate storeUpdate);

	void update(Username username, StoreOptionUpdate storeOptionUpdate);

	void delete(Username username, StoreId storeId);
}
