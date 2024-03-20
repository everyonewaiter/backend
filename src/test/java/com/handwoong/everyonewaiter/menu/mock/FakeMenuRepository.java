package com.handwoong.everyonewaiter.menu.mock;

import com.handwoong.everyonewaiter.menu.application.port.MenuRepository;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
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
