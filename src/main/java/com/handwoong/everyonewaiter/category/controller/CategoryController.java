package com.handwoong.everyonewaiter.category.controller;

import com.handwoong.everyonewaiter.category.controller.port.CategoryService;
import com.handwoong.everyonewaiter.category.controller.request.CategoryCreateRequest;
import com.handwoong.everyonewaiter.category.controller.request.CategoryUpdateRequest;
import com.handwoong.everyonewaiter.category.controller.response.CategoryResponses;
import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.dto.CategoryDelete;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/list")
	public ResponseEntity<ApiResponse<CategoryResponses>> categories(@RequestParam("store") final Long storeId) {
		final List<Category> categories = categoryService.findAllByStoreId(new StoreId(storeId));
		return ResponseEntity.ok(ApiResponse.success(CategoryResponses.from(categories)));
	}

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

	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> delete(
			@RequestParam("category") final Long id,
			@RequestParam("store") final Long storeId
	) {
		categoryService.delete(CategoryDelete.of(id, storeId));
		return ResponseEntity.ok(ApiResponse.success());
	}
}
