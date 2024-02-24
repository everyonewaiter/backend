package com.handwoong.everyonewaiter.medium.common.snippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;

import org.springframework.restdocs.snippet.Snippet;

public class CommonRequestSnippet {

	public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	public static final String AUTHORIZATION_HEADER_TYPE = "Bearer";

	public static final Snippet AUTHORIZATION_HEADER = requestHeaders(
			headerWithName("Authorization").description("액세스 토큰")
	);
	public static final Snippet USER_AUTHORIZATION_HEADER = requestHeaders(
			headerWithName("Authorization").description("사용자 권한 액세스 토큰")
	);
	public static final Snippet ADMIN_AUTHORIZATION_HEADER = requestHeaders(
			headerWithName("Authorization").description("관리자 권한 액세스 토큰")
	);
}
