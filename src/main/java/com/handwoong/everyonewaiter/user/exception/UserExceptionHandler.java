package com.handwoong.everyonewaiter.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
public class UserExceptionHandler {

    @ExceptionHandler({AlreadyExistsUsernameException.class})
    public ResponseEntity<ApiResponse<Object>> alreadyExistsUsername(
        final AlreadyExistsUsernameException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getUsername());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({InvalidUsernameFormatException.class})
    public ResponseEntity<ApiResponse<Object>> invalidUsernameFormat(
        final InvalidUsernameFormatException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getUsername());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({InvalidPasswordFormatException.class})
    public ResponseEntity<ApiResponse<Object>> invalidPasswordFormat(
        final InvalidPasswordFormatException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({UnauthorizedAccessException.class})
    public ResponseEntity<ApiResponse<Object>> unauthorizedAccess(
        final UnauthorizedAccessException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ApiResponse<Object>> userNotFound(
        final UserNotFoundException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(NOT_FOUND, request.getRequestURI(), errorMessage);
        return ResponseEntity
            .status(NOT_FOUND.value())
            .body(ApiResponse.error(errorMessage));
    }
}
