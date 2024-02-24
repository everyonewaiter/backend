package com.handwoong.everyonewaiter.medium.store.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class StoreResponseSnippet {

	public static final Snippet STORE_RESPONSES = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.stores[].id").type(JsonFieldType.NUMBER).description("매장 ID"),
			fieldWithPath("data.stores[].userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
			fieldWithPath("data.stores[].name").type(JsonFieldType.STRING).description("매장 명"),
			fieldWithPath("data.stores[].landlineNumber").type(JsonFieldType.STRING).description("매장 전화번호"),
			fieldWithPath("data.stores[].status").type(JsonFieldType.STRING).description("매장 영업 상태"),
			fieldWithPath("data.stores[].lastOpenedAt").type(JsonFieldType.STRING).description("매장 마지막 오픈일").optional(),
			fieldWithPath("data.stores[].lastClosedAt").type(JsonFieldType.STRING).description("매장 마지막 마감일").optional(),
			fieldWithPath("data.stores[].breakTimes[]").type(JsonFieldType.ARRAY).description("매장 브레이크 타임"),
			fieldWithPath("data.stores[].breakTimes[].id").type(JsonFieldType.NUMBER).description("브레이크 타임 ID"),
			fieldWithPath("data.stores[].breakTimes[].start").type(JsonFieldType.STRING).description("브레이크 타임 시작 시간"),
			fieldWithPath("data.stores[].breakTimes[].end").type(JsonFieldType.STRING).description("브레이크 타임 종료 시간"),
			fieldWithPath("data.stores[].breakTimes[].daysOfWeek[]").type(JsonFieldType.ARRAY)
					.description("브레이크 타임 적용 요일"),
			fieldWithPath("data.stores[].businessTimes[]").type(JsonFieldType.ARRAY).description("매장 영업 시간"),
			fieldWithPath("data.stores[].businessTimes[].id").type(JsonFieldType.NUMBER).description("영업 시간 ID"),
			fieldWithPath("data.stores[].businessTimes[].open").type(JsonFieldType.STRING).description("영업 시작 시간"),
			fieldWithPath("data.stores[].businessTimes[].close").type(JsonFieldType.STRING).description("영업 종료 시간"),
			fieldWithPath("data.stores[].businessTimes[].daysOfWeek[]").type(JsonFieldType.ARRAY).description("영업 요일"),
			fieldWithPath("data.stores[].option").type(JsonFieldType.OBJECT).description("매장 옵션"),
			fieldWithPath("data.stores[].option.id").type(JsonFieldType.NUMBER).description("옵션 ID"),
			fieldWithPath("data.stores[].option.useBreakTime").type(JsonFieldType.BOOLEAN)
					.description("브레이크 타임에 기능 사용 불가 여부"),
			fieldWithPath("data.stores[].option.useWaiting").type(JsonFieldType.BOOLEAN).description("웨이팅 기능 사용 여부"),
			fieldWithPath("data.stores[].option.useOrder").type(JsonFieldType.BOOLEAN).description("테이블 오더 기능 사용 여부"),
			fieldWithPath("data.stores[].timestamp.createdAt").type(JsonFieldType.STRING).description("매장 생성일"),
			fieldWithPath("data.stores[].timestamp.updatedAt").type(JsonFieldType.STRING).description("매장 수정일")
	);
	public static final Snippet STORE_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("매장 ID"),
			fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
			fieldWithPath("data.name").type(JsonFieldType.STRING).description("매장 명"),
			fieldWithPath("data.landlineNumber").type(JsonFieldType.STRING).description("매장 전화번호"),
			fieldWithPath("data.status").type(JsonFieldType.STRING).description("매장 영업 상태"),
			fieldWithPath("data.lastOpenedAt").type(JsonFieldType.STRING).description("매장 마지막 오픈일").optional(),
			fieldWithPath("data.lastClosedAt").type(JsonFieldType.STRING).description("매장 마지막 마감일").optional(),
			fieldWithPath("data.breakTimes[]").type(JsonFieldType.ARRAY).description("매장 브레이크 타임"),
			fieldWithPath("data.breakTimes[].id").type(JsonFieldType.NUMBER).description("브레이크 타임 ID"),
			fieldWithPath("data.breakTimes[].start").type(JsonFieldType.STRING).description("브레이크 타임 시작 시간"),
			fieldWithPath("data.breakTimes[].end").type(JsonFieldType.STRING).description("브레이크 타임 종료 시간"),
			fieldWithPath("data.breakTimes[].daysOfWeek[]").type(JsonFieldType.ARRAY)
					.description("브레이크 타임 적용 요일"),
			fieldWithPath("data.businessTimes[]").type(JsonFieldType.ARRAY).description("매장 영업 시간"),
			fieldWithPath("data.businessTimes[].id").type(JsonFieldType.NUMBER).description("영업 시간 ID"),
			fieldWithPath("data.businessTimes[].open").type(JsonFieldType.STRING).description("영업 시작 시간"),
			fieldWithPath("data.businessTimes[].close").type(JsonFieldType.STRING).description("영업 종료 시간"),
			fieldWithPath("data.businessTimes[].daysOfWeek[]").type(JsonFieldType.ARRAY).description("영업 요일"),
			fieldWithPath("data.option").type(JsonFieldType.OBJECT).description("매장 옵션"),
			fieldWithPath("data.option.id").type(JsonFieldType.NUMBER).description("옵션 ID"),
			fieldWithPath("data.option.useBreakTime").type(JsonFieldType.BOOLEAN)
					.description("브레이크 타임에 기능 사용 불가 여부"),
			fieldWithPath("data.option.useWaiting").type(JsonFieldType.BOOLEAN).description("웨이팅 기능 사용 여부"),
			fieldWithPath("data.option.useOrder").type(JsonFieldType.BOOLEAN).description("테이블 오더 기능 사용 여부"),
			fieldWithPath("data.timestamp.createdAt").type(JsonFieldType.STRING).description("매장 생성일"),
			fieldWithPath("data.timestamp.updatedAt").type(JsonFieldType.STRING).description("매장 수정일")
	);
	public static final Snippet CUD_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA.optional()
	);
}
