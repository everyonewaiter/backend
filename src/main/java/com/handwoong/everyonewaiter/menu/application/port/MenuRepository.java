package com.handwoong.everyonewaiter.menu.application.port;

import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;

public interface MenuRepository {

	Menu save(Menu menu);

	Menu findByIdOrElseThrow(MenuId menuId);
}
