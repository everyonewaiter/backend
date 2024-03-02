package com.handwoong.everyonewaiter.store.application.port;

import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.UserId;
import java.util.List;

public interface StoreRepository {

	List<Store> findAllByUserId(UserId userId);

	Store save(Store store);

	Store findByIdOrElseThrow(StoreId id);

	Store findByIdAndUserIdOrElseThrow(StoreId id, UserId userId);

	void delete(Store store);
}
