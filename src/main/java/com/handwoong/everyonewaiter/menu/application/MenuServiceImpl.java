package com.handwoong.everyonewaiter.menu.application;

import com.handwoong.everyonewaiter.menu.application.port.MenuRepository;
import com.handwoong.everyonewaiter.menu.controller.port.MenuService;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.domain.MenuValidator;
import com.handwoong.everyonewaiter.menu.dto.MenuCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;
	private final MenuValidator menuValidator;

	@Override
	@Transactional
	public MenuId create(final MenuCreate menuCreate) {
		final Menu menu = Menu.create(menuCreate, menuValidator);
		final Menu createdMenu = menuRepository.save(menu);
		return createdMenu.getId();
	}
}
