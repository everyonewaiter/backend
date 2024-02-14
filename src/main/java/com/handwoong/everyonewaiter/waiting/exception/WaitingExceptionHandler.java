package com.handwoong.everyonewaiter.waiting.exception;

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
public class WaitingExceptionHandler {

    @ExceptionHandler({AlreadyExistsPhoneNumberException.class})
    public ResponseEntity<ApiResponse<Void>> alreadyExistsPhoneNumber(
        final AlreadyExistsPhoneNumberException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getPhoneNumber());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({WaitingNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> waitingNotFound(
        final WaitingNotFoundException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getResource());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }
}
