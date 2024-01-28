package com.handwoong.everyonewaiter.common.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
public class BaseAuthenticationHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    protected void handle(
        final HttpStatus status,
        final String message,
        final HttpServletResponse response
    ) throws IOException {
        final ApiResponse<Object> errorResponse = ApiResponse.error(message);
        final String body = mapper.writeValueAsString(errorResponse);
        response.setStatus(status.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
    }
}
