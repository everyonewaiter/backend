package com.handwoong.everyonewaiter.notification.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record AlimTalkMessageRequest(
		String to,
		String content,
		List<TemplateButton> buttons,
		boolean useSmsFailover
) {

	public static AlimTalkMessageRequest of(final String to, final String content, final List<TemplateButton> buttons) {
		return AlimTalkMessageRequest.builder()
				.to(to)
				.content(content)
				.buttons(buttons)
				.useSmsFailover(true)
				.build();
	}
}
