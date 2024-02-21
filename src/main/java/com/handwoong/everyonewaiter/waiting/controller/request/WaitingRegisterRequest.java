package com.handwoong.everyonewaiter.waiting.controller.request;

import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.common.domain.PhoneNumber.PHONE_NUMBER_REGEX;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MAX_ADULT;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MAX_ADULT_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MIN_ADULT;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingAdult.MIN_ADULT_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MAX_CHILDREN;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MAX_CHILDREN_MESSAGE;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MIN_CHILDREN;
import static com.handwoong.everyonewaiter.waiting.domain.WaitingChildren.MIN_CHILDREN_MESSAGE;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.waiting.domain.WaitingAdult;
import com.handwoong.everyonewaiter.waiting.domain.WaitingChildren;
import com.handwoong.everyonewaiter.waiting.dto.WaitingRegister;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record WaitingRegisterRequest(
		@NotNull
		Long storeId,

		@Min(value = MIN_ADULT, message = MIN_ADULT_MESSAGE)
		@Max(value = MAX_ADULT, message = MAX_ADULT_MESSAGE)
		int adult,

		@Min(value = MIN_CHILDREN, message = MIN_CHILDREN_MESSAGE)
		@Max(value = MAX_CHILDREN, message = MAX_CHILDREN_MESSAGE)
		int children,

		@NotBlank(message = PHONE_NUMBER_FORMAT_MESSAGE)
		@Pattern(regexp = PHONE_NUMBER_REGEX, message = PHONE_NUMBER_FORMAT_MESSAGE)
		String phoneNumber
) {

	public WaitingRegister toDomainDto() {
		return WaitingRegister.builder()
				.storeId(new StoreId(storeId))
				.adult(new WaitingAdult(adult))
				.children(new WaitingChildren(children))
				.phoneNumber(new PhoneNumber(phoneNumber))
				.build();
	}
}
