package com.handwoong.everyonewaiter.menu.controller;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenu;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.menu.controller.request.MenuCreateRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuOptionGroupRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuSingleSelectOptionRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuUpdateRequest;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import java.util.List;
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
}
