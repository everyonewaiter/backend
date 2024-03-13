package com.handwoong.everyonewaiter.notification.domain;

import com.handwoong.everyonewaiter.notification.dto.TemplateButton;
import com.handwoong.everyonewaiter.store.domain.StoreId;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Template implements TemplateContent {
	REGISTER("register") {
		@Override
		public String content(final Object... args) {
			final String template = """
					%s을(를) 찾아주셔서 감사합니다.
					     
					고객님께서는 대기 명단에 정상적으로 접수 되셨습니다.
					     
					■ 인원
					- 성인 %s명
					- 아동 %s명
					■ 대기번호 : %s번
					■ 내 앞 대기팀 : %s팀
					■ 매장 전화번호 : %s
					     
					찾아주신 분들께 맛있고 더 나은 서비스를 제공하기 위해 노력하겠습니다.
					""";
			return String.format(template, args);
		}

		@Override
		public List<TemplateButton> buttons(final StoreId storeId, final UUID uniqueCode, final String clientUrl) {
			final TemplateButton cancelButton = TemplateButton.createWebLinkButton(
					"대기 취소하기", MessageFormat.format("{0}/waiting/{1}/{2}", clientUrl, storeId, uniqueCode));
			final TemplateButton myTurnButton = TemplateButton.createWebLinkButton(
					"내 순서 확인하기", MessageFormat.format("{0}/waiting/{1}/{2}/turn", clientUrl, storeId, uniqueCode));
			final TemplateButton previewButton = TemplateButton.createWebLinkButton(
					"메뉴 미리보기", MessageFormat.format("{0}/menus/{1}", clientUrl, storeId));
			return List.of(cancelButton, myTurnButton, previewButton);
		}
	},
	ENTRY("entry") {
		@Override
		public String content(final Object... args) {
			final String template = """
					안녕하세요.
					%s입니다.
					     
					대기번호 %s번 고객님 지금 입장 해 주세요.
					     
					■ 5분 이내 미 입장 시 대기 접수가 자동 취소되며, 다시 웨이팅 등록을 해주셔야 합니다.
					""";
			return String.format(template, args);
		}

		@Override
		public List<TemplateButton> buttons(final StoreId storeId, final UUID uniqueCode, final String clientUrl) {
			final TemplateButton cancelButton = TemplateButton.createWebLinkButton(
					"대기 취소하기", MessageFormat.format("{0}/waiting/{1}/{2}", clientUrl, storeId, uniqueCode));
			return List.of(cancelButton);
		}
	},
	CANCEL("cancel") {
		@Override
		public String content(final Object... args) {
			final String template = """
					안녕하세요.
					%s입니다.
					     
					대기번호 %s번 고객님 대기 취소가 완료되었습니다.
					     
					오늘도 좋은 하루 보내세요.
					""";
			return String.format(template, args);
		}

		@Override
		public List<TemplateButton> buttons(final StoreId storeId, final UUID uniqueCode, final String clientUrl) {
			return List.of();
		}
	};

	private final String code;
}
