package com.handwoong.everyonewaiter.waiting.controller.request;

import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.dto.WaitingCancel;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record WaitingCancelRequest(
		@NotNull
		Long storeId,

		@NotNull
		UUID uniqueCode
) {

	public WaitingCancel toDomainDto() {
		return WaitingCancel.builder()
				.storeId(new StoreId(storeId))
				.uniqueCode(uniqueCode)
				.build();
	}
}
