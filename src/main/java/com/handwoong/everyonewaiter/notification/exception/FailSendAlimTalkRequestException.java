package com.handwoong.everyonewaiter.notification.exception;

import com.handwoong.everyonewaiter.common.exception.BaseException;
import java.net.URI;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

@Getter
public class FailSendAlimTalkRequestException extends BaseException {

	private final HttpMethod method;
	private final URI uri;
	private final HttpStatusCode code;
	private final String reason;

	public FailSendAlimTalkRequestException(
			final String message,
			final HttpMethod method,
			final URI uri,
			final HttpStatusCode code,
			final String reason
	) {
		super(message);
		this.method = method;
		this.uri = uri;
		this.code = code;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return """
				요청 METHOD : [%s]
				요청 URI : [%s]
				응답 CODE : [%s]
				실패 사유 : [%s]
				""".formatted(method, uri, code, reason);
	}
}
