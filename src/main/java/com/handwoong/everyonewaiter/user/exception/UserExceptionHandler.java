package com.handwoong.everyonewaiter.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.exception.ExceptionLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    @ExceptionHandler({AlreadyExistsUsernameException.class})
    public ResponseEntity<ApiResponse<Void>> alreadyExistsUsername(
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
    public ResponseEntity<ApiResponse<Void>> invalidUsernameFormat(
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
    public ResponseEntity<ApiResponse<Void>> invalidPasswordFormat(
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
    public ResponseEntity<ApiResponse<Void>> unauthorizedAccess(
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
    public ResponseEntity<ApiResponse<Void>> userNotFound(
        final UserNotFoundException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(NOT_FOUND, request.getRequestURI(), errorMessage, exception.getResource());
        return ResponseEntity
            .status(NOT_FOUND.value())
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> usernameNotFound(
        final UsernameNotFoundException exception,
        final HttpServletRequest request
    ) {
        final String errorMessage = exception.getMessage();
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), errorMessage);
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler({AccountExpiredException.class})
    public ResponseEntity<ApiResponse<Void>> accountExpired(
        final AccountExpiredException exception,
        final HttpServletRequest request
    ) {
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error("계정이 휴면 상태입니다."));
    }

    @ExceptionHandler({LockedException.class})
    public ResponseEntity<ApiResponse<Void>> locked(
        final LockedException exception,
        final HttpServletRequest request
    ) {
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error("계정이 잠금 상태입니다."));
    }

    @ExceptionHandler({CredentialsExpiredException.class})
    public ResponseEntity<ApiResponse<Void>> credentialsExpired(
        final CredentialsExpiredException exception,
        final HttpServletRequest request
    ) {
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error("인증이 완료되지 않은 계정입니다."));
    }

    @ExceptionHandler({DisabledException.class})
    public ResponseEntity<ApiResponse<Void>> disabled(
        final DisabledException exception,
        final HttpServletRequest request
    ) {
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error("탈퇴된 계정입니다."));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiResponse<Void>> badCredentials(
        final BadCredentialsException exception,
        final HttpServletRequest request
    ) {
        ExceptionLogger.warn(BAD_REQUEST, request.getRequestURI(), exception.getMessage());
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error("아이디와 비밀번호를 확인 해주세요."));
    }
}
