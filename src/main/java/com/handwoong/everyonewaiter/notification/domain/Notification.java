package com.handwoong.everyonewaiter.notification.domain;

import com.handwoong.everyonewaiter.common.domain.AggregateRoot;
import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification extends AggregateRoot {

	private final NotificationId id;
	private final StoreId storeId;
	private final Template template;
	private final String requestId;
	private final String requestTime;
	private final String statusCode;
	private final String statusName;
	private final String messageId;
	private final PhoneNumber phoneNumber;
	private final String content;
	private final String requestStatusCode;
	private final String requestStatusName;
	private final String requestStatusDesc;
	private final boolean useSmsFailover;

	public static Notification create(final StoreId storeId, final Template template, final AlimTalkResponse response) {
		return Notification.builder()
				.storeId(storeId)
				.template(template)
				.requestId(response.requestId())
				.requestTime(response.requestTime())
				.statusCode(response.statusCode())
				.statusName(response.statusName())
				.messageId(response.messages().get(0).messageId())
				.phoneNumber(new PhoneNumber(response.messages().get(0).to()))
				.content(response.messages().get(0).content())
				.requestStatusCode(response.messages().get(0).requestStatusCode())
				.requestStatusName(response.messages().get(0).requestStatusName())
				.requestStatusDesc(response.messages().get(0).requestStatusDesc())
				.useSmsFailover(response.messages().get(0).useSmsFailover())
				.build();
	}
}
