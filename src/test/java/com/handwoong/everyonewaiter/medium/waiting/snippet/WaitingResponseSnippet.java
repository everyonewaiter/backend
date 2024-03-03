package com.handwoong.everyonewaiter.medium.waiting.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class WaitingResponseSnippet {

	public static final Snippet WAITING_COUNT_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("웨이팅 수")
	);
	public static final Snippet WAITING_TURN_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.turn").type(JsonFieldType.NUMBER).description("내 순서 '-1'은 완료 또는 취소인 경우")
	);
	public static final Snippet WAITING_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("웨이팅 ID"),
			fieldWithPath("data.storeId").type(JsonFieldType.NUMBER).description("매장 ID"),
			fieldWithPath("data.adult").type(JsonFieldType.NUMBER).description("웨이팅 어른 인원 수"),
			fieldWithPath("data.children").type(JsonFieldType.NUMBER).description("웨이팅 어린이 인원 수"),
			fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("웨이팅 번호"),
			fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("고객 휴대폰 번호"),
			fieldWithPath("data.status").type(JsonFieldType.STRING).description("웨이팅 상태 'WAIT' | 'CANCEL' | 'ENTRY'"),
			fieldWithPath("data.notificationType").type(JsonFieldType.STRING)
					.description("웨이팅 알림 상태 타입 'REGISTER' | 'READY' | 'CANCEL' | 'WAIT'"),
			fieldWithPath("data.uniqueCode").type(JsonFieldType.STRING).description("웨이팅 고유 UUID"),
			fieldWithPath("data.timestamp.createdAt").type(JsonFieldType.STRING).description("웨이팅 생성일"),
			fieldWithPath("data.timestamp.updatedAt").type(JsonFieldType.STRING).description("웨이팅 수정일")
	);
}
