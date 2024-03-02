package com.handwoong.everyonewaiter.waiting.controller.port;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;

public interface WaitingService {

	int count(Username username, StoreId storeId);

	WaitingId register(WaitingRegister waitingRegister);

	void cancel(WaitingCancel waitingCancel);
}
