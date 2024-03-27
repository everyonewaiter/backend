package com.handwoong.everyonewaiter.menu.controller.response;

import com.handwoong.everyonewaiter.menu.domain.Menu;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuResponses(List<MenuResponse> menus) {

	public static MenuResponses from(final List<Menu> menus) {
		return MenuResponses.builder()
				.menus(menus.stream().map(MenuResponse::from).toList())
				.build();
	}
}
