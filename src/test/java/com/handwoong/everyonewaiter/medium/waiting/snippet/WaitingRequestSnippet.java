package com.handwoong.everyonewaiter.medium.waiting.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class WaitingRequestSnippet {

	public static final Snippet QUERY_PARAM_STORE_ID = queryParameters(
			parameterWithName("store").description("매장 ID")
	);
	public static final Snippet QUERY_PARAM_STORE_ID_AND_UNIQUE_CODE = queryParameters(
			parameterWithName("store").description("매장 ID"),
			parameterWithName("code").description("웨이팅 고유 UUID")
	);
	public static final Snippet REGISTER_REQUEST = requestFields(
			fieldWithPath("storeId")
					.type(JsonFieldType.NUMBER)
					.description("매장 ID"),
			fieldWithPath("adult")
					.type(JsonFieldType.NUMBER)
					.description("어른 인원 수")
					.attributes(constraints("20명 이하")),
			fieldWithPath("children")
					.type(JsonFieldType.NUMBER)
					.description("어린이 인원 수")
					.attributes(constraints("20명 이하")),
			fieldWithPath("phoneNumber")
					.type(JsonFieldType.STRING)
					.description("고객 휴대폰 번호")
					.attributes(constraints("01로 시작하는 7~8자리 숫자"))
	);
	public static final Snippet CANCEL_REQUEST = requestFields(
			fieldWithPath("storeId")
					.type(JsonFieldType.NUMBER)
					.description("매장 ID"),
			fieldWithPath("uniqueCode")
					.type(JsonFieldType.STRING)
					.description("웨이팅 고유 코드")
					.attributes(constraints("UUID"))
	);

	private WaitingRequestSnippet() {
	}
}
