package com.handwoong.everyonewaiter.menu.controller.port;

import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import com.handwoong.everyonewaiter.menu.dto.MenuUpdate;

public interface MenuService {

	MenuId create(MenuCreate menuCreate);

	void update(MenuUpdate menuUpdate);
}
