package com.handwoong.everyonewaiter.common.dto;

import lombok.Builder;

@Builder
public record ApiResponse<T>(
    ResultCode resultCode,
    String message,
    T data
) {

    public static <U> ApiResponse<U> of(
        final ResultCode resultCode,
        final String message,
        final U data
    ) {
        return ApiResponse.<U>builder()
            .resultCode(resultCode)
            .message(message)
            .data(data)
            .build();
    }

    public static <U> ApiResponse<U> success() {
        return success(null);
    }

    public static <U> ApiResponse<U> success(final U data) {
        return of(ResultCode.SUCCESS, null, data);
    }

    public static <U> ApiResponse<U> error(final String message) {
        return of(ResultCode.FAIL, message, null);
    }

    public enum ResultCode {
        SUCCESS,
        FAIL,
    }
}
