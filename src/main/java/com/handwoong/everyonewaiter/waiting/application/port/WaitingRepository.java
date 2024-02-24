package com.handwoong.everyonewaiter.waiting.application.port;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public interface WaitingRepository {

	Waiting save(Waiting waiting);

	boolean existsByPhoneNumber(PhoneNumber phoneNumber);

	int countByAfterStoreOpen(StoreId storeId, WaitingStatus status, LocalDateTime lastOpenedAt);

	Waiting findByStoreIdAndUniqueCodeOrElseThrow(StoreId storeId, UUID uniqueCode);
}
