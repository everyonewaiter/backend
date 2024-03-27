package com.handwoong.everyonewaiter.medium.menu.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class MenuResponseSnippet {

	public static final Snippet MENUS_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.menus[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
			fieldWithPath("data.menus[].storeId").type(JsonFieldType.NUMBER).description("매장 ID"),
			fieldWithPath("data.menus[].categoryId").type(JsonFieldType.NUMBER).description("카테고리 ID"),
			fieldWithPath("data.menus[].name").type(JsonFieldType.STRING).description("메뉴 이름"),
			fieldWithPath("data.menus[].description").type(JsonFieldType.STRING).description("메뉴 설명"),
			fieldWithPath("data.menus[].image").type(JsonFieldType.STRING).description("메뉴 이미지"),
			fieldWithPath("data.menus[].price").type(JsonFieldType.NUMBER).description("메뉴 가격"),
			fieldWithPath("data.menus[].spicy").type(JsonFieldType.NUMBER).description("메뉴 맵기 단계"),
			fieldWithPath("data.menus[].printBillInKitchen").type(JsonFieldType.BOOLEAN).description("메뉴 주방 출력 여부"),
			fieldWithPath("data.menus[].status").type(JsonFieldType.STRING).description("메뉴 상태")
					.attributes(constraints("BASIC | SOLD_OUT | HIDE")),
			fieldWithPath("data.menus[].label").type(JsonFieldType.STRING).description("메뉴 라벨")
					.attributes(constraints("BASIC | NEW | BEST | REPRESENT")),
			fieldWithPath("data.menus[].optionGroups[].id").type(JsonFieldType.NUMBER).description("옵션 그룹 ID")
					.optional(),
			fieldWithPath("data.menus[].optionGroups[].name").type(JsonFieldType.STRING).description("옵션 그룹 이름")
					.optional(),
			fieldWithPath("data.menus[].optionGroups[].useOptionPrice").type(JsonFieldType.BOOLEAN)
					.description("옵션 가격 사용 여부").optional(),
			fieldWithPath("data.menus[].optionGroups[].singleSelectOptions").type(JsonFieldType.ARRAY)
					.description("단일 선택 옵션 배열"),
			fieldWithPath("data.menus[].optionGroups[].singleSelectOptions[].id").type(JsonFieldType.NUMBER)
					.description("단일 선택 옵션 ID").optional(),
			fieldWithPath("data.menus[].optionGroups[].singleSelectOptions[].name").type(JsonFieldType.STRING)
					.description("단일 선택 옵션 이름").optional(),
			fieldWithPath("data.menus[].optionGroups[].singleSelectOptions[].price").type(JsonFieldType.NUMBER)
					.description("단일 선택 옵션 가격").optional(),
			fieldWithPath("data.menus[].optionGroups[].singleSelectOptions[].isDefault").type(JsonFieldType.BOOLEAN)
					.description("단일 선택 옵션 기본값 여부").optional(),
			fieldWithPath("data.menus[].optionGroups[].multiSelectOptions").type(JsonFieldType.ARRAY)
					.description("다중 선택 옵션 배열"),
			fieldWithPath("data.menus[].optionGroups[].multiSelectOptions[].id").type(JsonFieldType.NUMBER)
					.description("다중 선택 옵션 ID").optional(),
			fieldWithPath("data.menus[].optionGroups[].multiSelectOptions[].name").type(JsonFieldType.STRING)
					.description("다중 선택 옵션 이름").optional(),
			fieldWithPath("data.menus[].optionGroups[].multiSelectOptions[].price").type(JsonFieldType.NUMBER)
					.description("다중 선택 옵션 가격").optional()
	);
}
