package com.handwoong.everyonewaiter.common.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler extends BaseAuthenticationHandler implements AccessDeniedHandler {

	@Override
	public void handle(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final AccessDeniedException accessDeniedException
	) throws IOException {
		handle(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.", response);
	}
}
