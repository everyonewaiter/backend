package com.handwoong.everyonewaiter.store.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record StoreBusinessTimeResponse(
		Long id,

		@JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
		LocalTime open,

		@JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
		LocalTime close,

		List<String> daysOfWeek
) {

	public static StoreBusinessTimeResponse from(final StoreBusinessTime storeBusinessTime) {
		return StoreBusinessTimeResponse.builder()
				.id(storeBusinessTime.getId().value())
				.open(storeBusinessTime.getOpen())
				.close(storeBusinessTime.getClose())
				.daysOfWeek(storeBusinessTime.getDaysOfWeek().daysOfWeek().stream().map(Enum::name).toList())
				.build();
	}
}
