package com.handwoong.everyonewaiter.menu.application.port;

import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.util.List;

public interface MenuRepository {

	Menu save(Menu menu);

	Menu findByIdOrElseThrow(MenuId menuId);

	void delete(Menu menu);

	List<Menu> findAllByStoreId(StoreId storeId);
}
