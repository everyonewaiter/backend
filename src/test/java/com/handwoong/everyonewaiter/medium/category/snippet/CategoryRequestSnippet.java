package com.handwoong.everyonewaiter.medium.category.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class CategoryRequestSnippet {

    public static final Snippet CREATE_REQUEST = requestFields(
        fieldWithPath("storeId")
            .type(JsonFieldType.NUMBER)
            .description("매장 ID"),
        fieldWithPath("name")
            .type(JsonFieldType.STRING)
            .description("카테고리명")
            .attributes(constraints("20자 이하")),
        fieldWithPath("icon")
            .type(JsonFieldType.STRING)
            .description("카테고리 아이콘 이름")
            .attributes(constraints("20자 이하"))
    );
    public static final Snippet UPDATE_REQUEST = requestFields(
        fieldWithPath("id")
            .type(JsonFieldType.NUMBER)
            .description("카테고리 ID"),
        fieldWithPath("storeId")
            .type(JsonFieldType.NUMBER)
            .description("매장 ID"),
        fieldWithPath("name")
            .type(JsonFieldType.STRING)
            .description("카테고리명")
            .attributes(constraints("20자 이하")),
        fieldWithPath("icon")
            .type(JsonFieldType.STRING)
            .description("카테고리 아이콘 이름")
            .attributes(constraints("20자 이하"))
    );
}
