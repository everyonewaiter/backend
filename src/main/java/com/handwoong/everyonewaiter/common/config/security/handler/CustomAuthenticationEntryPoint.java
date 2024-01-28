package com.handwoong.everyonewaiter.common.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint extends BaseAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException
    ) throws IOException {
        handle(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다.", response);
    }
}
