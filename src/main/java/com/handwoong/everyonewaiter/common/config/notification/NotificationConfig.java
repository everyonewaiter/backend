package com.handwoong.everyonewaiter.common.config.notification;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class NotificationConfig {

	@Value("${naver.sens.access-key}")
	private String accessKey;

	@Value("${naver.sens.secret-key}")
	private String secretKey;

	@Value("${naver.sens.service.id}")
	private String serviceId;

	@Value("${naver.sens.channel.id}")
	private String channelName;
}
