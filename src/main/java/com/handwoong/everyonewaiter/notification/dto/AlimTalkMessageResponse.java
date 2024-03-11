package com.handwoong.everyonewaiter.notification.dto;

public record AlimTalkMessageResponse(
		String messageId,
		String countryCode,
		String to,
		String content,
		String requestStatusCode,
		String requestStatusName,
		String requestStatusDesc,
		boolean useSmsFailover
) {

}
