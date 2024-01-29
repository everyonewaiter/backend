package com.handwoong.everyonewaiter.user.controller.snippet;

import static com.handwoong.everyonewaiter.util.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class UserRequestSnippet {

    public static final Snippet JOIN_REQUEST = requestFields(
        fieldWithPath("username")
            .type(JsonFieldType.STRING)
            .description("사용자 아이디")
            .attributes(constraints("30자 이하")),
        fieldWithPath("password")
            .type(JsonFieldType.STRING)
            .description("비밀번호")
            .attributes(constraints("6자리 숫자")),
        fieldWithPath("phoneNumber")
            .type(JsonFieldType.STRING)
            .description("휴대폰 번호")
            .attributes(constraints("01로 시작하는 7~8자리 숫자"))
    );

    private UserRequestSnippet() {
    }
}
