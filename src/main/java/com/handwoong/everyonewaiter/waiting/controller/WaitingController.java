package com.handwoong.everyonewaiter.waiting.controller;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.waiting.controller.port.WaitingService;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingRegisterRequest;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting")
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid final WaitingRegisterRequest request) {
        final WaitingId waitingId = waitingService.register(request.toDomainDto());
        return ResponseEntity
            .created(URI.create(waitingId.toString()))
            .body(ApiResponse.success());
    }
}
