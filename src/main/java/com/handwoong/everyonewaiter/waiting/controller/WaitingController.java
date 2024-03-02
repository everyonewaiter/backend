package com.handwoong.everyonewaiter.waiting.controller;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import com.handwoong.everyonewaiter.waiting.controller.port.WaitingService;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingCancelRequest;
import com.handwoong.everyonewaiter.waiting.controller.request.WaitingRegisterRequest;
import com.handwoong.everyonewaiter.waiting.controller.response.WaitingCountResponse;
import com.handwoong.everyonewaiter.waiting.domain.WaitingId;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting")
public class WaitingController {

	private final WaitingService waitingService;

	@GetMapping("/stores/{storeId}")
	public ResponseEntity<ApiResponse<WaitingCountResponse>> count(@PathVariable("storeId") final Long storeId) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		final int count = waitingService.count(username, new StoreId(storeId));
		return ResponseEntity.ok(ApiResponse.success(WaitingCountResponse.from(count)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid final WaitingRegisterRequest request) {
		final WaitingId waitingId = waitingService.register(request.toDomainDto());
		return ResponseEntity
				.created(URI.create(waitingId.toString()))
				.body(ApiResponse.success());
	}

	@PutMapping("/cancel")
	public ResponseEntity<ApiResponse<Void>> cancel(@RequestBody @Valid final WaitingCancelRequest request) {
		waitingService.cancel(request.toDomainDto());
		return ResponseEntity.ok(ApiResponse.success());
	}
}
