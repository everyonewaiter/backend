package com.handwoong.everyonewaiter.menu.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
public class MenuExceptionHandler {

	@ExceptionHandler({InvalidMenuNameFormatException.class})
	public ResponseEntity<ApiResponse<Void>> invalidMenuNameFormat(
			final InvalidMenuNameFormatException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getName());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({InvalidMenuDescriptionFormatException.class})
	public ResponseEntity<ApiResponse<Void>> invalidMenuDescriptionFormat(
			final InvalidMenuDescriptionFormatException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getDescription());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({InvalidMenuSpicySizeException.class})
	public ResponseEntity<ApiResponse<Void>> invalidMenuSpicySize(
			final InvalidMenuSpicySizeException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getSpicy());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({InvalidOptionNameFormatException.class})
	public ResponseEntity<ApiResponse<Void>> invalidOptionNameFormat(
			final InvalidOptionNameFormatException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getName());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}
}
