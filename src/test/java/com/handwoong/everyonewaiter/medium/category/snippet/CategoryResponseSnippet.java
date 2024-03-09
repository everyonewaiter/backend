package com.handwoong.everyonewaiter.medium.category.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class CategoryResponseSnippet {

	public static final Snippet CATEGORIES_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.categories[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
			fieldWithPath("data.categories[].storeId").type(JsonFieldType.NUMBER).description("매장 ID"),
			fieldWithPath("data.categories[].name").type(JsonFieldType.STRING).description("카테고리 이름"),
			fieldWithPath("data.categories[].icon").type(JsonFieldType.STRING).description("카테고리 아이콘"),
			fieldWithPath("data.categories[].timestamp.createdAt").type(JsonFieldType.STRING).description("카테고리 생성일"),
			fieldWithPath("data.categories[].timestamp.updatedAt").type(JsonFieldType.STRING).description("카테고리 수정일")
	);
}
