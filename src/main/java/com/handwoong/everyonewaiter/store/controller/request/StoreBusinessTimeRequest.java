package com.handwoong.everyonewaiter.store.controller.request;

import com.handwoong.everyonewaiter.store.domain.DayOfWeek;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTime;
import com.handwoong.everyonewaiter.store.domain.StoreDaysOfWeek;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public record StoreBusinessTimeRequest(
		@NotNull
		@DateTimeFormat(pattern = "HH:mm")
		LocalTime open,

		@NotNull
		@DateTimeFormat(pattern = "HH:mm")
		LocalTime close,

		@NotEmpty(message = "영업 시간의 요일을 하나 이상 등록해주세요.")
		List<DayOfWeek> daysOfWeek
) {

	public static final String MIN_BUSINESS_TIME_MESSAGE = "영업 시간은 최소 1개 이상 등록해야합니다.";

	public StoreBusinessTime toDomain() {
		return StoreBusinessTime.builder()
				.open(open)
				.close(close.withSecond(59).withNano(999_999))
				.daysOfWeek(new StoreDaysOfWeek(daysOfWeek))
				.build();
	}
}
