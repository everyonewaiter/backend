package com.handwoong.everyonewaiter.menu.application;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenu;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenuOptionGroup;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.category.exception.CategoryNotFoundException;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuDescription;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuName;
import com.handwoong.everyonewaiter.menu.domain.MenuOptionGroups;
import com.handwoong.everyonewaiter.menu.domain.MenuSpicy;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import com.handwoong.everyonewaiter.menu.domain.Money;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;
import com.handwoong.everyonewaiter.menu.exception.MenuNotFoundException;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.store.exception.StoreNotFoundException;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.exception.UserNotFoundException;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuServiceImplTest {

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
	void Should_Create_When_ValidMenuCreate() {
		// given
		final MenuCreate menuCreate = MenuCreate.builder()
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.name(new MenuName("수비드 소고기 스테이크"))
				.description(new MenuDescription("부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크"))
				.image("")
				.price(new Money(29_900))
				.spicy(new MenuSpicy(0))
				.printBillInKitchen(true)
				.status(MenuStatus.BASIC)
				.label(MenuLabel.REPRESENT)
				.optionGroups(new MenuOptionGroups(List.of(aMenuOptionGroup().build())))
				.build();

		// when
		final MenuId menuId = testContainer.menuService.create(menuCreate);

		// then
		assertThat(menuId).isEqualTo(new MenuId(1L));
	}

	@Test
	void Should_ThrowException_When_CreateUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final MenuCreate menuCreate = MenuCreate.builder()
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.create(menuCreate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CreateStoreNotFound() {
		// given
		final MenuCreate menuCreate = MenuCreate.builder()
				.storeId(new StoreId(2L))
				.categoryId(new CategoryId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.create(menuCreate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_CreateCategoryNotFound() {
		// given
		final MenuCreate menuCreate = MenuCreate.builder()
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(2L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.create(menuCreate))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}

	@Test
	void Should_Update_When_ValidMenuUpdate() {
		// given
		final MenuId menuId = new MenuId(1L);
		final MenuName menuName = new MenuName("소고기 스테이크 (수비드)");
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(menuId)
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.name(menuName)
				.description(new MenuDescription("부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크"))
				.image("")
				.price(new Money(29_900))
				.spicy(new MenuSpicy(0))
				.printBillInKitchen(true)
				.status(MenuStatus.BASIC)
				.label(MenuLabel.REPRESENT)
				.optionGroups(new MenuOptionGroups(List.of(aMenuOptionGroup().build())))
				.build();

		// when
		testContainer.menuService.update(menuUpdate);
		final Menu updatedMenu = testContainer.menuRepository.findByIdOrElseThrow(menuId);

		// then
		assertThat(updatedMenu.getName()).isEqualTo(menuName);
	}

	@Test
	void Should_ThrowException_When_UpdateUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(new MenuId(1L))
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.update(menuUpdate))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateMenuNotFound() {
		// given
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(new MenuId(2L))
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.update(menuUpdate))
				.isInstanceOf(MenuNotFoundException.class)
				.hasMessage("메뉴를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateStoreNotFound() {
		// given
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(new MenuId(1L))
				.storeId(new StoreId(2L))
				.categoryId(new CategoryId(1L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.update(menuUpdate))
				.isInstanceOf(StoreNotFoundException.class)
				.hasMessage("매장을 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_UpdateCategoryNotFound() {
		// given
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(new MenuId(1L))
				.storeId(new StoreId(1L))
				.categoryId(new CategoryId(2L))
				.build();

		// expect
		assertThatThrownBy(() -> testContainer.menuService.update(menuUpdate))
				.isInstanceOf(CategoryNotFoundException.class)
				.hasMessage("카테고리를 찾을 수 없습니다.");
	}

	@Test
	void Should_Delete_When_ValidMenuId() {
		// given
		final MenuId menuId = new MenuId(1L);

		// expect
		assertThatCode(() -> testContainer.menuService.delete(menuId))
				.doesNotThrowAnyException();
	}

	@Test
	void Should_ThrowException_When_DeleteUserNotFound() {
		// given
		testContainer.setSecurityContextAuthentication(new Username("username"));
		final MenuId menuId = new MenuId(1L);

		// expect
		assertThatThrownBy(() -> testContainer.menuService.delete(menuId))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("사용자를 찾을 수 없습니다.");
	}

	@Test
	void Should_ThrowException_When_DeleteMenuNotFound() {
		// given
		final MenuId menuId = new MenuId(2L);

		// expect
		assertThatThrownBy(() -> testContainer.menuService.delete(menuId))
				.isInstanceOf(MenuNotFoundException.class)
				.hasMessage("메뉴를 찾을 수 없습니다.");
	}
}
