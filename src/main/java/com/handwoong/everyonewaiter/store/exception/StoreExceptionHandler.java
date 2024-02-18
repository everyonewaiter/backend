package com.handwoong.everyonewaiter.store.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.exception.ExceptionLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StoreExceptionHandler {

    @ExceptionHandler({InvalidStoreNameFormatException.class})
    public ResponseEntity<ApiResponse<Void>> invalidStoreNameFormat(
        final InvalidStoreNameFormatException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getName());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({InvalidLandlineNumberFormatException.class})
    public ResponseEntity<ApiResponse<Void>> invalidLandlineNumberFormat(
        final InvalidLandlineNumberFormatException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getLandlineNumber());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({StoreNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> storeNotFound(
        final StoreNotFoundException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage, exception.getResource());
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(errorMessage));
    }
}
