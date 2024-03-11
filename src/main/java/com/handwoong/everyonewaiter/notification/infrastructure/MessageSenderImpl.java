package com.handwoong.everyonewaiter.notification.infrastructure;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.handwoong.everyonewaiter.common.application.port.TimeHolder;
import com.handwoong.everyonewaiter.common.config.notification.NotificationConfig;
import com.handwoong.everyonewaiter.common.exception.ExceptionLogger;
import com.handwoong.everyonewaiter.notification.application.port.MessageSender;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkRequest;
import com.handwoong.everyonewaiter.notification.dto.AlimTalkResponse;
import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import com.handwoong.everyonewaiter.notification.exception.FailMakeSignatureException;
import com.handwoong.everyonewaiter.notification.exception.FailSendAlimTalkRequestException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

	private static final String BASE_NAVER_URL = "https://sens.apigw.ntruss.com";

	private final TimeHolder timeHolder;
	private final NotificationConfig notificationConfig;

	@Override
	public AlimTalkResponse sendAlimTalk(
			final String to,
			final String code,
			final String content,
			final List<TemplateButton> buttons
	) {
		final String uri = "/alimtalk/v2/services/%s/messages".formatted(notificationConfig.getServiceId());
		final AlimTalkRequest alimTalkRequest =
				AlimTalkRequest.of(notificationConfig.getChannelName(), to, code, content, buttons);

		final RestClient client = setupRestClient(uri);
		return client.post()
				.uri(BASE_NAVER_URL + uri)
				.body(alimTalkRequest)
				.retrieve()
				.onStatus(HttpStatusCode::isError, (request, response) -> {
					final FailSendAlimTalkRequestException exception = new FailSendAlimTalkRequestException(
							"알림톡 전송 요청에 실패하였습니다.",
							request.getMethod(),
							request.getURI(),
							response.getStatusCode(),
							response.getStatusText()
					);
					ExceptionLogger.error(
							INTERNAL_SERVER_ERROR,
							exception.getUri().getPath(),
							exception.getMessage(),
							exception,
							exception.toString()
					);
					throw exception;
				})
				.body(AlimTalkResponse.class);
	}

	private RestClient setupRestClient(final String uri) {
		final String timestamp = Long.toString(timeHolder.millis());
		return RestClient.builder()
				.defaultHeaders(httpHeaders -> {
					httpHeaders.setContentType(MediaType.APPLICATION_JSON);
					httpHeaders.set("x-ncp-apigw-timestamp", timestamp);
					httpHeaders.set("x-ncp-iam-access-key", notificationConfig.getAccessKey());
					httpHeaders.set("x-ncp-apigw-signature-v2", makeSignature(uri, timestamp));
				})
				.build();
	}

	private String makeSignature(final String uri, final String timestamp) {
		final String accessKey = notificationConfig.getAccessKey();
		final String secretKey = notificationConfig.getSecretKey();
		final String message = """
				POST %s
				%s
				%s\
				""".formatted(uri, timestamp, accessKey);

		final Mac mac;
		final SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (final NoSuchAlgorithmException | InvalidKeyException exception) {
			throw new FailMakeSignatureException("시그니처 생성에 실패하였습니다.");
		}

		final byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
		return Base64.encodeBase64String(rawHmac);
	}
}
