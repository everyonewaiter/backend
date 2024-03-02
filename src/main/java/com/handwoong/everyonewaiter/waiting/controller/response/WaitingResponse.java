package com.handwoong.everyonewaiter.waiting.controller.response;

import com.handwoong.everyonewaiter.common.domain.DomainTimestamp;
import com.handwoong.everyonewaiter.waiting.domain.Waiting;
import com.handwoong.everyonewaiter.waiting.domain.WaitingNotificationType;
import com.handwoong.everyonewaiter.waiting.domain.WaitingStatus;
import java.util.UUID;
import lombok.Builder;

@Builder
public record WaitingResponse(
		Long id,
		Long storeId,
		int adult,
		int children,
		int number,
		String phoneNumber,
		WaitingStatus status,
		WaitingNotificationType notificationType,
		UUID uniqueCode,
		DomainTimestamp timestamp
) {

	public static WaitingResponse from(final Waiting waiting) {
		return WaitingResponse.builder()
				.id(waiting.getId().value())
				.storeId(waiting.getStoreId().value())
				.adult(waiting.getAdult().value())
				.children(waiting.getChildren().value())
				.number(waiting.getNumber().value())
				.phoneNumber(waiting.getPhoneNumber().toString())
				.status(waiting.getStatus())
				.notificationType(waiting.getNotificationType())
				.uniqueCode(waiting.getUniqueCode())
				.timestamp(waiting.getTimestamp())
				.build();
	}
}
