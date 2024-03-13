package com.handwoong.everyonewaiter.notification.dto;

import java.util.List;

public record AlimTalkResponse(
		String requestId,
		String requestTime,
		String statusCode,
		String statusName,
		List<AlimTalkMessageResponse> messages
) {

}
