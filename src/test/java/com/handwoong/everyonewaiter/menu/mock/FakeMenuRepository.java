package com.handwoong.everyonewaiter.menu.mock;

import com.handwoong.everyonewaiter.menu.application.port.MenuRepository;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.exception.MenuNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FakeMenuRepository implements MenuRepository {

	private final Map<Long, Menu> database = new HashMap<>();

	private Long sequence = 1L;

	@Override
	public Menu save(final Menu menu) {
		final Long id = Objects.nonNull(menu.getId()) ? menu.getId().value() : sequence++;
		final Menu newMenu = create(id, menu);
		database.put(id, newMenu);
		return newMenu;
	}

	@Override
	public Menu findByIdOrElseThrow(final MenuId menuId) {
		return database.values()
				.stream()
				.filter(menu -> menu.getId().equals(menuId))
				.findAny()
				.orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다.", menuId.toString()));
	}

	@Override
	public void delete(final Menu menu) {
		database.remove(menu.getId().value());
	}

	private Menu create(final Long id, final Menu menu) {
		return Menu.builder()
				.id(new MenuId(id))
				.storeId(menu.getStoreId())
				.categoryId(menu.getCategoryId())
				.name(menu.getName())
				.description(menu.getDescription())
				.image(menu.getImage())
				.price(menu.getPrice())
				.spicy(menu.getSpicy())
				.printBillInKitchen(menu.isPrintBillInKitchen())
				.status(menu.getStatus())
				.label(menu.getLabel())
				.optionGroups(menu.getOptionGroups())
				.build();
	}
}
