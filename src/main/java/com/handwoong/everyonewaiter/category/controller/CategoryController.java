package com.handwoong.everyonewaiter.category.controller;

import com.handwoong.everyonewaiter.category.controller.port.CategoryService;
import com.handwoong.everyonewaiter.category.controller.request.CategoryCreateRequest;
import com.handwoong.everyonewaiter.category.controller.request.CategoryUpdateRequest;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid final CategoryCreateRequest request) {
		final CategoryId categoryId = categoryService.create(request.toDomainDto());
		return ResponseEntity
				.created(URI.create(categoryId.toString()))
				.body(ApiResponse.success());
	}

	@PutMapping
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid final CategoryUpdateRequest request) {
		categoryService.update(request.toDomainDto());
		return ResponseEntity.ok(ApiResponse.success());
	}
}
