package com.handwoong.everyonewaiter.notification.dto;

import lombok.Builder;

@Builder
public record TemplateButton(
		String type,
		String name,
		String linkMobile,
		String linkPc,
		String schemeIos,
		String schemeAndroid
) {

	public static TemplateButton createWebLinkButton(final String name, final String link) {
		return TemplateButton.builder()
				.type("WL")
				.name(name)
				.linkPc(link)
				.linkMobile(link)
				.build();
	}
}
