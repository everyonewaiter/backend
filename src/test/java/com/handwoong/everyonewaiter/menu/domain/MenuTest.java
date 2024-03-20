package com.handwoong.everyonewaiter.menu.domain;

import static com.handwoong.everyonewaiter.util.Fixtures.aCategory;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenu;
import static com.handwoong.everyonewaiter.util.Fixtures.aMenuOptionGroup;
import static com.handwoong.everyonewaiter.util.Fixtures.aStore;
import static com.handwoong.everyonewaiter.util.Fixtures.aUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.category.domain.Category;
import com.handwoong.everyonewaiter.category.domain.CategoryId;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;
import com.handwoong.everyonewaiter.store.domain.Store;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.util.TestContainer;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuTest {

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
	}

	@Test
	void Should_Create_When_ValidMenuCreate() {
		// given
		final MenuName menuName = new MenuName("수비드 소고기 스테이크");
		final MenuCreate menuCreate = MenuCreate.builder()
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
		final Menu menu = Menu.create(menuCreate, testContainer.menuValidator);

		// then
		assertThat(menu.getName()).isEqualTo(menuName);
	}

	@Test
	void Should_Update_When_ValidMenuUpdate() {
		// given
		final Menu menu = aMenu().build();
		final MenuName menuName = new MenuName("소고기 스테이크 (수비드)");
		final MenuUpdate menuUpdate = MenuUpdate.builder()
				.menuId(new MenuId(1L))
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
		final Menu updatedMenu = menu.update(menuUpdate, testContainer.menuValidator);

		// then
		assertThat(updatedMenu.getName()).isEqualTo(menuName);
	}
}
