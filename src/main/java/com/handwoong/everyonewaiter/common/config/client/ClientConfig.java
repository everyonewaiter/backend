package com.handwoong.everyonewaiter.common.config.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ClientConfig {

	@Value("${client.url}")
	private String clientUrl;
}
