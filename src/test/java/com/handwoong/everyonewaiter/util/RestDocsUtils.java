package com.handwoong.everyonewaiter.util;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.snippet.Attributes.key;

import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.operation.preprocess.HeadersModifyingOperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Attributes.Attribute;

public class RestDocsUtils {

    public static final FieldDescriptor COMMON_API_RESPONSE_RESULT_CODE =
        fieldWithPath("resultCode")
            .type(JsonFieldType.STRING)
            .description("상태 코드 SUCCESS | FAIL");
    public static final FieldDescriptor COMMON_API_RESPONSE_MESSAGE =
        fieldWithPath("message")
            .type(JsonFieldType.STRING)
            .description("결과 메시지");
    public static final FieldDescriptor COMMON_API_RESPONSE_DATA =
        fieldWithPath("data")
            .type(JsonFieldType.OBJECT)
            .description("결과 데이터");

    private static RequestSpecification specification;

    private RestDocsUtils() {
    }

    public static RestDocumentationFilter getFilter() {
        return document(
            "{class-name}/{method-name}",
            getRequestPreprocessor(),
            getResponsePreprocessor()
        );
    }

    public static OperationPreprocessor removeHeaders() {
        final HeadersModifyingOperationPreprocessor modifyHeaders = Preprocessors.modifyHeaders();
        for (final String header : HttpHeader.getUnusedHeaders()) {
            modifyHeaders.remove(header);
        }
        return modifyHeaders;
    }

    public static OperationRequestPreprocessor getRequestPreprocessor() {
        return preprocessRequest(prettyPrint());
    }

    public static OperationResponsePreprocessor getResponsePreprocessor() {
        return preprocessResponse(prettyPrint());
    }

    public static RequestSpecification getSpecification() {
        return specification;
    }

    public static void setSpecification(final RequestSpecification specification) {
        RestDocsUtils.specification = specification;
    }

    public static Attribute constraints(final String value) {
        return key("constraints").value(value);
    }
}
