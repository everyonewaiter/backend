package com.handwoong.everyonewaiter.menu.infrastructure;

import static com.handwoong.everyonewaiter.menu.infrastructure.QMenuEntity.menuEntity;

import com.handwoong.everyonewaiter.menu.application.port.MenuRepository;
import com.handwoong.everyonewaiter.menu.domain.Menu;
import com.handwoong.everyonewaiter.menu.domain.MenuId;
import com.handwoong.everyonewaiter.menu.exception.MenuNotFoundException;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

	private final JPAQueryFactory queryFactory;
	private final MenuJpaRepository menuJpaRepository;

	@Override
	public Menu save(final Menu menu) {
		return menuJpaRepository.save(MenuEntity.from(menu)).toModel();
	}

	@Override
	public Menu findByIdOrElseThrow(final MenuId menuId) {
		return menuJpaRepository.findById(menuId.value())
				.orElseThrow(() -> new MenuNotFoundException("메뉴를 찾을 수 없습니다.", menuId.toString()))
				.toModel();
	}

	@Override
	public void delete(final Menu menu) {
		menuJpaRepository.delete(MenuEntity.from(menu));
	}

	@Override
	public List<Menu> findAllByStoreId(final StoreId storeId) {
		return queryFactory.selectFrom(menuEntity)
				.where(menuEntity.storeId.eq(storeId.value()))
				.fetch()
				.stream()
				.map(MenuEntity::toModel)
				.toList();
	}
}
