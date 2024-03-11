package com.handwoong.everyonewaiter.notification.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.exception.ExceptionLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotificationExceptionHandler {

	@ExceptionHandler({FailMakeSignatureException.class})
	public ResponseEntity<ApiResponse<Void>> failMakeSignature(
			final FailMakeSignatureException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.error(INTERNAL_SERVER_ERROR, request.getRequestURI(), exception.getMessage(), exception);
		return ResponseEntity
				.internalServerError()
				.body(ApiResponse.error("메시지 전송에 실패하였습니다. 잠시 후 다시 시도해주세요."));
	}

	@ExceptionHandler({FailSendAlimTalkRequestException.class})
	public ResponseEntity<ApiResponse<Void>> failSendAlimTalkRequest() {
		return ResponseEntity
				.internalServerError()
				.body(ApiResponse.error("알림톡 전송 요청에 실패하였습니다. 잠시 후 다시 시도해주세요."));
	}
}
