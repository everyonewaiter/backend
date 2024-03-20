package com.handwoong.everyonewaiter.menu.controller.port;

import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;

public interface MenuService {

	MenuId create(MenuCreate menuCreate);
}
