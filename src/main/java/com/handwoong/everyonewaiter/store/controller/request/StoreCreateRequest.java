package com.handwoong.everyonewaiter.store.controller.request;

import static com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest.MIN_BUSINESS_TIME_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_REGEX;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH_MESSAGE;

import com.handwoong.everyonewaiter.store.domain.LandlineNumber;
import com.handwoong.everyonewaiter.store.domain.StoreBreakTimes;
import com.handwoong.everyonewaiter.store.domain.StoreBusinessTimes;
import com.handwoong.everyonewaiter.store.domain.StoreName;
import com.handwoong.everyonewaiter.store.dto.StoreCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record StoreCreateRequest(
		@NotBlank(message = STORE_NAME_EMPTY_MESSAGE)
		@Size(max = STORE_NAME_MAX_LENGTH, message = STORE_NAME_MAX_LENGTH_MESSAGE)
		String name,

		@NotBlank(message = LANDLINE_NUMBER_FORMAT_MESSAGE)
		@Pattern(regexp = LANDLINE_NUMBER_REGEX, message = LANDLINE_NUMBER_FORMAT_MESSAGE)
		String landlineNumber,

		@NotNull
		List<@Valid StoreBreakTimeRequest> breakTimes,

		@NotEmpty(message = MIN_BUSINESS_TIME_MESSAGE)
		List<@Valid StoreBusinessTimeRequest> businessTimes,

		@NotNull
		@Valid StoreCreateOptionRequest option
) {

	public StoreCreate toDomainDto() {
		return StoreCreate.builder()
				.name(new StoreName(name))
				.landlineNumber(new LandlineNumber(landlineNumber))
				.businessTimes(
						new StoreBusinessTimes(
								businessTimes.stream()
										.map(StoreBusinessTimeRequest::toDomain)
										.toList()
						)
				)
				.breakTimes(
						new StoreBreakTimes(
								breakTimes.stream()
										.map(StoreBreakTimeRequest::toDomain)
										.toList()
						)
				)
				.option(option.toDomain())
				.build();
	}
}
