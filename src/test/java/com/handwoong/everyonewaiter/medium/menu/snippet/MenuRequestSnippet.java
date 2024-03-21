package com.handwoong.everyonewaiter.medium.menu.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class MenuRequestSnippet {

	public static final Snippet PATH_PARAM_MENU_ID = pathParameters(
			parameterWithName("id").description("메뉴 ID")
	);
	public static final Snippet CREATE_REQUEST = requestFields(
			fieldWithPath("storeId")
					.type(JsonFieldType.NUMBER)
					.description("매장 ID"),
			fieldWithPath("categoryId")
					.type(JsonFieldType.NUMBER)
					.description("카테고리 ID"),
			fieldWithPath("name")
					.type(JsonFieldType.STRING)
					.description("메뉴 이름")
					.attributes(constraints("20자 이하")),
			fieldWithPath("description")
					.type(JsonFieldType.STRING)
					.description("메뉴 설명")
					.attributes(constraints("100자 이하")),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("메뉴 사진 URL"),
			fieldWithPath("price")
					.type(JsonFieldType.NUMBER)
					.description("메뉴 가격"),
			fieldWithPath("spicy")
					.type(JsonFieldType.NUMBER)
					.description("메뉴 맵기 단계")
					.attributes(constraints("0이상 10 이하")),
			fieldWithPath("printBillInKitchen")
					.type(JsonFieldType.BOOLEAN)
					.description("주방 빌지 출력 여부"),
			fieldWithPath("status")
					.type(JsonFieldType.STRING)
					.description("메뉴 상태")
					.attributes(constraints("BASIC | SOLD_OUT | HIDE")),
			fieldWithPath("label")
					.type(JsonFieldType.STRING)
					.description("메뉴 라벨")
					.attributes(constraints("BASIC | NEW | BEST | REPRESENT")),
			fieldWithPath("optionGroups")
					.type(JsonFieldType.ARRAY)
					.description("옵션 그룹"),
			fieldWithPath("optionGroups[].name")
					.type(JsonFieldType.STRING)
					.description("옵션 그룹 명")
					.attributes(constraints("30자 이하")),
			fieldWithPath("optionGroups[].useOptionPrice")
					.type(JsonFieldType.BOOLEAN)
					.description("옵션의 가격 사용 여부"),
			fieldWithPath("optionGroups[].singleSelectOptions")
					.type(JsonFieldType.ARRAY)
					.description("단일 선택 옵션"),
			fieldWithPath("optionGroups[].singleSelectOptions[].name")
					.type(JsonFieldType.STRING)
					.description("단일 선택 옵션 이름")
					.attributes(constraints("30자 이하"))
					.optional(),
			fieldWithPath("optionGroups[].singleSelectOptions[].price")
					.type(JsonFieldType.NUMBER)
					.description("단일 선택 옵션 가격")
					.optional(),
			fieldWithPath("optionGroups[].singleSelectOptions[].isDefault")
					.type(JsonFieldType.BOOLEAN)
					.description("단일 선택 옵션 기본값인지 여부")
					.optional(),
			fieldWithPath("optionGroups[].multiSelectOptions")
					.type(JsonFieldType.ARRAY)
					.description("다중 선택 옵션"),
			fieldWithPath("optionGroups[].multiSelectOptions[].name")
					.type(JsonFieldType.STRING)
					.description("다중 선택 옵션 이름")
					.attributes(constraints("30자 이하"))
					.optional(),
			fieldWithPath("optionGroups[].multiSelectOptions[].price")
					.type(JsonFieldType.NUMBER)
					.description("다중 선택 옵션 가격")
					.optional()
	);
	public static final Snippet UPDATE_REQUEST = requestFields(
			fieldWithPath("menuId")
					.type(JsonFieldType.NUMBER)
					.description("메뉴 ID"),
			fieldWithPath("storeId")
					.type(JsonFieldType.NUMBER)
					.description("매장 ID"),
			fieldWithPath("categoryId")
					.type(JsonFieldType.NUMBER)
					.description("카테고리 ID"),
			fieldWithPath("name")
					.type(JsonFieldType.STRING)
					.description("메뉴 이름")
					.attributes(constraints("20자 이하")),
			fieldWithPath("description")
					.type(JsonFieldType.STRING)
					.description("메뉴 설명")
					.attributes(constraints("100자 이하")),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("메뉴 사진 URL"),
			fieldWithPath("price")
					.type(JsonFieldType.NUMBER)
					.description("메뉴 가격"),
			fieldWithPath("spicy")
					.type(JsonFieldType.NUMBER)
					.description("메뉴 맵기 단계")
					.attributes(constraints("0이상 10 이하")),
			fieldWithPath("printBillInKitchen")
					.type(JsonFieldType.BOOLEAN)
					.description("주방 빌지 출력 여부"),
			fieldWithPath("status")
					.type(JsonFieldType.STRING)
					.description("메뉴 상태")
					.attributes(constraints("BASIC | SOLD_OUT | HIDE")),
			fieldWithPath("label")
					.type(JsonFieldType.STRING)
					.description("메뉴 라벨")
					.attributes(constraints("BASIC | NEW | BEST | REPRESENT")),
			fieldWithPath("optionGroups")
					.type(JsonFieldType.ARRAY)
					.description("옵션 그룹"),
			fieldWithPath("optionGroups[].name")
					.type(JsonFieldType.STRING)
					.description("옵션 그룹 명")
					.attributes(constraints("30자 이하")),
			fieldWithPath("optionGroups[].useOptionPrice")
					.type(JsonFieldType.BOOLEAN)
					.description("옵션의 가격 사용 여부"),
			fieldWithPath("optionGroups[].singleSelectOptions")
					.type(JsonFieldType.ARRAY)
					.description("단일 선택 옵션"),
			fieldWithPath("optionGroups[].singleSelectOptions[].name")
					.type(JsonFieldType.STRING)
					.description("단일 선택 옵션 이름")
					.attributes(constraints("30자 이하"))
					.optional(),
			fieldWithPath("optionGroups[].singleSelectOptions[].price")
					.type(JsonFieldType.NUMBER)
					.description("단일 선택 옵션 가격")
					.optional(),
			fieldWithPath("optionGroups[].singleSelectOptions[].isDefault")
					.type(JsonFieldType.BOOLEAN)
					.description("단일 선택 옵션 기본값인지 여부")
					.optional(),
			fieldWithPath("optionGroups[].multiSelectOptions")
					.type(JsonFieldType.ARRAY)
					.description("다중 선택 옵션"),
			fieldWithPath("optionGroups[].multiSelectOptions[].name")
					.type(JsonFieldType.STRING)
					.description("다중 선택 옵션 이름")
					.attributes(constraints("30자 이하"))
					.optional(),
			fieldWithPath("optionGroups[].multiSelectOptions[].price")
					.type(JsonFieldType.NUMBER)
					.description("다중 선택 옵션 가격")
					.optional()
	);
}
