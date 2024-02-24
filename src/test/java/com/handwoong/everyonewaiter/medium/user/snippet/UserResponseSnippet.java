package com.handwoong.everyonewaiter.medium.user.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class UserResponseSnippet {

	public static final Snippet JOIN_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA.optional()
	);

	public static final Snippet LOGIN_RESPONSE = responseFields(
			COMMON_API_RESPONSE_RESULT_CODE,
			COMMON_API_RESPONSE_MESSAGE.optional(),
			COMMON_API_RESPONSE_DATA,
			fieldWithPath("data.token")
					.type(JsonFieldType.STRING)
					.description("액세스 토큰")
	);

	private UserResponseSnippet() {
	}
}
