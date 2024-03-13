package com.handwoong.everyonewaiter.notification.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AlimTalkRequest(
		String plusFriendId,
		String templateCode,
		List<AlimTalkMessageRequest> messages
) {

	public static AlimTalkRequest of(
			final String channelName,
			final String to,
			final String code,
			final String content,
			final List<TemplateButton> buttons
	) {
		return AlimTalkRequest.builder()
				.plusFriendId(channelName)
				.templateCode(code)
				.messages(List.of(AlimTalkMessageRequest.of(to, content, buttons)))
				.build();
	}
}
