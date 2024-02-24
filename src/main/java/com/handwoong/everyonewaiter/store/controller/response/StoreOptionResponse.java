package com.handwoong.everyonewaiter.store.controller.response;

import com.handwoong.everyonewaiter.store.domain.StoreOption;
import lombok.Builder;

@Builder
public record StoreOptionResponse(
		Long id,
		boolean useBreakTime,
		boolean useWaiting,
		boolean useOrder
) {

	public static StoreOptionResponse from(final StoreOption storeOption) {
		return StoreOptionResponse.builder()
				.id(storeOption.getId().value())
				.useBreakTime(storeOption.isUseBreakTime())
				.useWaiting(storeOption.isUseWaiting())
				.useOrder(storeOption.isUseOrder())
				.build();
	}
}
