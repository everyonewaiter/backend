package com.handwoong.everyonewaiter.notification.infrastructure;

import com.handwoong.everyonewaiter.notification.application.port.MessageSender;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkMessageResponse;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class FakeMessageSender implements MessageSender {

	@Override
	public AlimTalkResponse sendAlimTalk(
			final String to,
			final String code,
			final String content,
			final List<TemplateButton> buttons
	) {
		return new AlimTalkResponse(
				"requestId",
				"requestTime",
				"statusCode",
				"statusName",
				List.of(
						new AlimTalkMessageResponse(
								"messageId",
								"countryCode",
								"to",
								"content",
								"requestStatusCode",
								"requestStatusName",
								"requestStatusDesc",
								true
						)
				)
		);
	}
}
