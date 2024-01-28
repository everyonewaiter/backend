package com.handwoong.everyonewaiter.util;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import io.restassured.specification.RequestSpecification;
import org.springframework.restdocs.operation.preprocess.HeadersModifyingOperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

public class RestDocsUtils {

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
}
