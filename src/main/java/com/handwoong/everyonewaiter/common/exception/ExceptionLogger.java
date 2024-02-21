package com.handwoong.everyonewaiter.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionLogger {

	private static final String MESSAGE_FORMAT = "[%s] %s %s %s";

	public static void warn(final HttpStatus status, final String path, final String message) {
		final String logMessage = String.format(
				MESSAGE_FORMAT, status.name(), path, status.value(), message);
		log.warn(logMessage);
	}

	public static void warn(
			final HttpStatus status,
			final String path,
			final String message,
			final Object field
	) {
		final String logMessage = String.format(
				MESSAGE_FORMAT, status.name(), path, status.value(), message + " - " + field);
		log.warn(logMessage);
	}

	public static void error(
			final HttpStatus status,
			final String path,
			final String message,
			final Exception exception
	) {
		final String logMessage = String.format(
				MESSAGE_FORMAT, status.name(), path, status.value(), message);
		log.error(logMessage, exception);
	}

	public static void error(
			final HttpStatus status,
			final String path,
			final String message,
			final Exception exception,
			final Object field
	) {
		final String logMessage = String.format(
				MESSAGE_FORMAT, status.name(), path, status.value(), message + " - " + field);
		log.error(logMessage, exception);
	}
}
