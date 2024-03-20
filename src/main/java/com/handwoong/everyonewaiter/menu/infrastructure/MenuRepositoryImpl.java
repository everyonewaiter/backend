package com.handwoong.everyonewaiter.menu.infrastructure;

import com.handwoong.everyonewaiter.menu.application.port.MenuRepository;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

	private final MenuJpaRepository menuJpaRepository;

	@Override
	public Menu save(final Menu menu) {
		return menuJpaRepository.save(MenuEntity.from(menu)).toModel();
	}
}
