package com.handwoong.everyonewaiter.store.controller.response;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record StoreResponse(
		Long id,
		Long userId,
		String name,
		String landlineNumber,
		StoreStatus status,
		LocalDateTime lastOpenedAt,
		LocalDateTime lastClosedAt,
		List<StoreBreakTimeResponse> breakTimes,
		List<StoreBusinessTimeResponse> businessTimes,
		StoreOptionResponse option,
		DomainTimestamp timestamp
) {

	public static StoreResponse from(final Store store) {
		return StoreResponse.builder()
				.id(store.getId().value())
				.userId(store.getUserId().value())
				.name(store.getName().toString())
				.landlineNumber(store.getLandlineNumber().toString())
				.status(store.getStatus())
				.lastOpenedAt(store.getLastOpenedAt())
				.lastClosedAt(store.getLastClosedAt())
				.breakTimes(
						store.getBreakTimes()
								.breakTimes()
								.stream()
								.map(StoreBreakTimeResponse::from)
								.toList()
				)
				.businessTimes(
						store.getBusinessTimes()
								.businessTimes()
								.stream()
								.map(StoreBusinessTimeResponse::from)
								.toList()
				)
				.option(StoreOptionResponse.from(store.getOption()))
				.timestamp(store.getTimestamp())
				.build();
	}
}
