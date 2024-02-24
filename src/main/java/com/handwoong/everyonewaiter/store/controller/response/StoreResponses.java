package com.handwoong.everyonewaiter.store.controller.response;

import com.handwoong.everyonewaiter.store.domain.Store;
import java.util.List;
import lombok.Builder;

@Builder
public record StoreResponses(List<StoreResponse> stores) {

	public static StoreResponses from(final List<Store> stores) {
		return StoreResponses.builder()
				.stores(stores.stream().map(StoreResponse::from).toList())
				.build();
	}
}
