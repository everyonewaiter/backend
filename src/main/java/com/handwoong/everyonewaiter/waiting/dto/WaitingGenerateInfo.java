package com.handwoong.everyonewaiter.waiting.dto;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingTurn;
import lombok.Builder;

@Builder
public record WaitingGenerateInfo(Store store, WaitingNumber number, WaitingTurn turn) {

	public StoreId storeId() {
		return store.getId();
	}

	public StoreName storeName() {
		return store.getName();
	}

	public LandlineNumber landlineNumber() {
		return store.getLandlineNumber();
	}
}
