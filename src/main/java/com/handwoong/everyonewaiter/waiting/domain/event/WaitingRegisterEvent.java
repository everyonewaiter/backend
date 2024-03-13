package com.handwoong.everyonewaiter.waiting.domain.event;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNumber;
import com.handwoong.everyonewaiter.waiting.domain.WaitingTurn;
import com.handwoong.everyonewaiter.waiting.dto.WaitingGenerateInfo;
import java.util.UUID;

public record WaitingRegisterEvent(
		WaitingGenerateInfo waitingGenerateInfo,
		WaitingAdult adult,
		WaitingChildren children,
		UUID uniqueCode,
		PhoneNumber phoneNumber
) {

	public StoreId storeId() {
		return waitingGenerateInfo.storeId();
	}

	public StoreName storeName() {
		return waitingGenerateInfo.storeName();
	}

	public LandlineNumber landlineNumber() {
		return waitingGenerateInfo.landlineNumber();
	}

	public WaitingNumber number() {
		return waitingGenerateInfo.number();
	}

	public WaitingTurn turn() {
		return waitingGenerateInfo.turn();
	}
}
