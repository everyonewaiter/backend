package com.handwoong.everyonewaiter.notification.application.port;

import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import java.util.List;

public interface MessageSender {

	AlimTalkResponse sendAlimTalk(String to, String code, String content, List<TemplateButton> buttons);
}
