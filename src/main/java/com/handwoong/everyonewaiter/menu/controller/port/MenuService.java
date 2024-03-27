package com.handwoong.everyonewaiter.menu.controller.port;

import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;

public interface MenuService {

	MenuId create(MenuCreate menuCreate);

	void update(MenuUpdate menuUpdate);

	void delete(MenuId id);

	List<Menu> findAllByStoreId(StoreId storeId);
}
