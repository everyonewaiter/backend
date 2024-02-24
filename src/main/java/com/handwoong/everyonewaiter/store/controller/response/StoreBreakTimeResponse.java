package com.handwoong.everyonewaiter.store.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTime;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@Builder
public record StoreBreakTimeResponse(
		Long id,

		@JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
		LocalTime start,

		@JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
		LocalTime end,

		List<String> daysOfWeek
) {

	public static StoreBreakTimeResponse from(final StoreBreakTime storeBreakTime) {
		return StoreBreakTimeResponse.builder()
				.id(storeBreakTime.getId().value())
				.start(storeBreakTime.getStart())
				.end(storeBreakTime.getEnd())
				.daysOfWeek(storeBreakTime.getDaysOfWeek().daysOfWeek().stream().map(Enum::name).toList())
				.build();
	}
}
