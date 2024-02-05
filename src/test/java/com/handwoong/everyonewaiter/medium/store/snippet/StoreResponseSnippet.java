package com.handwoong.everyonewaiter.medium.store.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_DATA;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.COMMON_API_RESPONSE_RESULT_CODE;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.snippet.Snippet;

public class StoreResponseSnippet {

    public static final Snippet CREATE_RESPONSE = responseFields(
        COMMON_API_RESPONSE_RESULT_CODE,
        COMMON_API_RESPONSE_MESSAGE.optional(),
        COMMON_API_RESPONSE_DATA.optional()
    );
    public static final Snippet UPDATE_RESPONSE = responseFields(
        COMMON_API_RESPONSE_RESULT_CODE,
        COMMON_API_RESPONSE_MESSAGE.optional(),
        COMMON_API_RESPONSE_DATA.optional()
    );
}
