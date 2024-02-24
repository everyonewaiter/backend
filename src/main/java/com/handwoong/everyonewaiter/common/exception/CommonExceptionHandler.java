package com.handwoong.everyonewaiter.common.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Order
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<Void>> illegalArgument(
			final IllegalArgumentException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({InvalidPhoneNumberFormatException.class})
	public ResponseEntity<ApiResponse<Void>> invalidPhoneNumberFormat(
			final InvalidPhoneNumberFormatException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getPhoneNumber());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({InvalidJwtTokenException.class})
	public ResponseEntity<ApiResponse<Void>> invalidJwtToken(
			final InvalidJwtTokenException exception,
			final HttpServletRequest request
	) {
		final String errorMessage = exception.getMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ApiResponse<Void>> methodArgumentNotValid(
			final MethodArgumentNotValidException exception,
			final HttpServletRequest request
	) {
		final FieldError fieldError = parseFieldError(exception);
		final String field = fieldError.getField();
		final String errorMessage = fieldError.getDefaultMessage();
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, field);
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(errorMessage));
	}

	private FieldError parseFieldError(final MethodArgumentNotValidException exception) {
		return exception.getBindingResult()
				.getFieldErrors()
				.get(0);
	}

	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<ApiResponse<Void>> methodNotSupported(
			final HttpRequestMethodNotSupportedException exception,
			final HttpServletRequest request
	) {
		final String requestURI = request.getRequestURI();
		final String errorMessage =
				requestURI + " 해당 경로는 " + exception.getMethod() + " 메서드를 지원하지 않습니다.";
		ExceptionLogger.warn(METHOD_NOT_ALLOWED, request.getRequestURI(), errorMessage);
		return ResponseEntity
				.status(METHOD_NOT_ALLOWED.value())
				.body(ApiResponse.error(errorMessage));
	}

	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ApiResponse<Void>> httpMessageNotReadable(
			final HttpMessageNotReadableException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("유효하지 않은 요청 페이로드입니다."));
	}

	@ExceptionHandler({MethodArgumentTypeMismatchException.class, ConversionFailedException.class})
	public ResponseEntity<ApiResponse<Void>> invalidPathValue(
			final Exception exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("요청 주소에 유효하지 않은 값이 있습니다."));
	}

	@ExceptionHandler({MissingServletRequestPartException.class})
	public ResponseEntity<ApiResponse<Void>> missingServletRequestPart(
			final MissingServletRequestPartException exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("파일을 첨부해주세요."));
	}

	@ExceptionHandler({MultipartException.class, FileUploadException.class})
	public ResponseEntity<ApiResponse<Void>> multipart(
			final Exception exception,
			final HttpServletRequest request
	) {
		ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error("Content-Type을 명시하였거나, FormData에 파일이 없습니다."));
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ApiResponse<Void>> exception(
			final Exception exception,
			final HttpServletRequest request
	) {
		final String errorMessage = "서버에 문제가 발생하였습니다.";
		ExceptionLogger.error(
				INTERNAL_SERVER_ERROR, request.getRequestURI(), errorMessage, exception);
		return ResponseEntity
				.internalServerError()
				.body(ApiResponse.error(errorMessage));
	}
}
