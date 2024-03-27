package com.handwoong.everyonewaiter.menu.controller;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.menu.controller.port.MenuService;
import com.handwoong.everyonewaiter.menu.controller.request.MenuCreateRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuUpdateRequest;
import com.handwoong.everyonewaiter.menu.controller.response.MenuResponses;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.store.domain.StoreId;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/list")
	public ResponseEntity<ApiResponse<MenuResponses>> findAllByStore(@RequestParam("store") final Long storeId) {
		final List<Menu> menus = menuService.findAllByStoreId(new StoreId(storeId));
		return ResponseEntity.ok(ApiResponse.success(MenuResponses.from(menus)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> create(@RequestBody @Valid final MenuCreateRequest request) {
		final MenuId menuId = menuService.create(request.toDomain());
		return ResponseEntity
				.created(URI.create(menuId.toString()))
				.body(ApiResponse.success());
	}

	@PutMapping
	public ResponseEntity<ApiResponse<Void>> update(@RequestBody @Valid final MenuUpdateRequest request) {
		menuService.update(request.toDomain());
		return ResponseEntity.ok(ApiResponse.success());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") final Long id) {
		menuService.delete(new MenuId(id));
		return ResponseEntity.ok(ApiResponse.success());
	}
}
