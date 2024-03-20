package com.handwoong.everyonewaiter.menu.application.port;

import com.handwoong.everyonewaiter.menu.domain.Menu;

public interface MenuRepository {

	Menu save(Menu menu);
}
