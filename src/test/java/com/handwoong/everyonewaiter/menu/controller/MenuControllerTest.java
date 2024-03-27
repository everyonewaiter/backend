package com.handwoong.everyonewaiter.menu.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenu;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.menu.controller.request.MenuCreateRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuOptionGroupRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuSingleSelectOptionRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuUpdateRequest;
import com.handwoong.everyonewaiter.menu.controller.response.MenuResponse;
import com.handwoong.everyonewaiter.menu.controller.response.MenuResponses;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.exception.MenuNotFoundException;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class MenuControllerTest {

	private TestContainer testContainer;

	@BeforeEach
	void setUp() {
		final Username username = new Username("handwoong");
		testContainer = new TestContainer();
		testContainer.setSecurityContextAuthentication(username);
		testContainer.setTimeHolder(LocalDateTime.of(2024, 2, 5, 18, 0, 0)); // 월요일 18시 0분

		final User user = aUser().build();
		testContainer.userRepository.save(user);

		final Store store = aStore().build();
		testContainer.storeRepository.save(store);

		final Category category = aCategory().build();
		testContainer.categoryRepository.save(category);

		final Menu menu = aMenu().build();
		testContainer.menuRepository.save(menu);
	}

	@Test
	void Should_Create_When_ValidRequest() {
		// given
		final MenuCreateRequest request = new MenuCreateRequest(
				1L,
				1L,
				"수비드 소고기 스테이크",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of(
						new MenuOptionGroupRequest(
								"맵기 조절",
								false,
								List.of(
										new MenuSingleSelectOptionRequest("안맵게", 0, false),
										new MenuSingleSelectOptionRequest("기본", 0, true)
								),
								List.of()
						)
				)
		);

		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.menuController.create(request);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(201);
	}

	@Test
	void Should_ThrowException_When_CreateUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final MenuCreateRequest request = new MenuCreateRequest(
				1L,
				1L,
				"수비드 소고기 스테이크",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.create(request))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CreateStoreNotFound() {
		// given
		final MenuCreateRequest request = new MenuCreateRequest(
				2L,
				1L,
				"수비드 소고기 스테이크",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.create(request))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CreateCategoryNotFound() {
		// given
		final MenuCreateRequest request = new MenuCreateRequest(
				1L,
				2L,
				"수비드 소고기 스테이크",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.create(request))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}

	@Test
	void Should_Update_When_ValidRequest() {
		// given
		final MenuUpdateRequest request = new MenuUpdateRequest(
				1L,
				1L,
				1L,
				"소고기 스테이크 (수비드)",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of(
						new MenuOptionGroupRequest(
								"맵기 조절",
								false,
								List.of(
										new MenuSingleSelectOptionRequest("안맵게", 0, false),
										new MenuSingleSelectOptionRequest("기본", 0, true)
								),
								List.of()
						)
				)
		);

		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.menuController.update(request);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void Should_ThrowException_When_UpdateUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final MenuUpdateRequest request = new MenuUpdateRequest(
				1L,
				1L,
				1L,
				"소고기 스테이크 (수비드)",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.update(request))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateMenuNotFound() {
		// given
		final MenuUpdateRequest request = new MenuUpdateRequest(
				2L,
				1L,
				1L,
				"소고기 스테이크 (수비드)",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.update(request))
				.isInstanceOf(MenuNotFoundException.class)
				.hasMessage("메뉴를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateStoreNotFound() {
		// given
		final MenuUpdateRequest request = new MenuUpdateRequest(
				1L,
				2L,
				1L,
				"소고기 스테이크 (수비드)",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.update(request))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateCategoryNotFound() {
		// given
		final MenuUpdateRequest request = new MenuUpdateRequest(
				1L,
				1L,
				2L,
				"소고기 스테이크 (수비드)",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of()
		);

		// expect
		assertThatThrownBy(() -> testContainer.menuController.update(request))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}

	@Test
	void Should_Delete_When_ValidRequest() {
		// given
		// when
		final ResponseEntity<ApiResponse<Void>> response = testContainer.menuController.delete(1L);

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
	}

	@Test
	void Should_ThrowException_When_DeleteUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));

		// expect
		assertThatThrownBy(() -> testContainer.menuController.delete(1L))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_DeleteMenuNotFound() {
		// expect
		assertThatThrownBy(() -> testContainer.menuController.delete(2L))
				.isInstanceOf(MenuNotFoundException.class)
				.hasMessage("메뉴를 찾을 수 없습니다.");
	}

	@Test
	void Should_FindMenus_When_ValidStoreId() {
		// given
		// when
		final ResponseEntity<ApiResponse<MenuResponses>> response = testContainer.menuController.findAllByStore(1L);
		final List<MenuResponse> result = Objects.requireNonNull(response.getBody()).data().menus();

		// then
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(result).hasSize(1);
	}
}
