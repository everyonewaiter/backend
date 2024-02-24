package com.handwoong.everyonewaiter.store.controller;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.controller.port.StoreService;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreOptionUpdateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreUpdateRequest;
import com.handwoong.everyonewaiter.store.controller.response.StoreResponse;
import com.handwoong.everyonewaiter.store.controller.response.StoreResponses;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.infrastructure.SecurityUtils;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public ResponseEntity<ApiResponse<StoreResponses>> findAllByUser() {
		final Username username = SecurityUtils.getAuthenticationUsername();
		final List<Store> stores = storeService.findAllByUsername(username);
		return ResponseEntity.ok(ApiResponse.success(StoreResponses.from(stores)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<StoreResponse>> findByUser(@PathVariable("id") final Long id) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		final Store store = storeService.findByIdAndUsername(new StoreId(id), username);
		return ResponseEntity.ok(ApiResponse.success(StoreResponse.from(store)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid final StoreCreateRequest request) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		final StoreId storeId = storeService.create(username, request.toDomainDto());
		return ResponseEntity
				.created(URI.create(storeId.toString()))
				.body(ApiResponse.success());
	}

	@PutMapping
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid final StoreUpdateRequest request) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		storeService.update(username, request.toDomainDto());
		return ResponseEntity.ok(ApiResponse.success());
	}

	@PutMapping("/option")
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid final StoreOptionUpdateRequest request) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		storeService.update(username, request.toDomainDto());
		return ResponseEntity.ok(ApiResponse.success());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") final Long id) {
		final Username username = SecurityUtils.getAuthenticationUsername();
		storeService.delete(username, new StoreId(id));
		return ResponseEntity.ok(ApiResponse.success());
	}
}
