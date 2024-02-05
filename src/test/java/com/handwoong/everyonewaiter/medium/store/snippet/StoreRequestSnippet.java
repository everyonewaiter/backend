package com.handwoong.everyonewaiter.medium.store.snippet;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class StoreRequestSnippet {

    public static final Snippet PATH_PARAM_STORE_ID = pathParameters(
        parameterWithName("id").description("매장 ID")
    );
    public static final Snippet CREATE_REQUEST = requestFields(
        fieldWithPath("name")
            .type(JsonFieldType.STRING)
            .description("매장명")
            .attributes(constraints("50자 이하")),
        fieldWithPath("landlineNumber")
            .type(JsonFieldType.STRING)
            .description("매장 전화번호")
            .attributes(constraints("ex) 0551234567")),
        fieldWithPath("breakTimes[]")
            .type(JsonFieldType.ARRAY)
            .description("브레이크 타임 시작 시간")
            .optional(),
        fieldWithPath("breakTimes[].start")
            .type(JsonFieldType.STRING)
            .description("브레이크 타임 시작 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("breakTimes[].end")
            .type(JsonFieldType.STRING)
            .description("브레이크 타임 종료 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("breakTimes[].daysOfWeek")
            .type(JsonFieldType.ARRAY)
            .description("브레이크 타임 요일")
            .attributes(constraints("MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY")),
        fieldWithPath("businessTimes[].open")
            .type(JsonFieldType.STRING)
            .description("영업 시작 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("businessTimes[].close")
            .type(JsonFieldType.STRING)
            .description("영업 종료 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("businessTimes[].daysOfWeek")
            .type(JsonFieldType.ARRAY)
            .description("영업 요일")
            .attributes(constraints("MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY")),
        fieldWithPath("option.useBreakTime")
            .type(JsonFieldType.BOOLEAN)
            .description("브레이크 타임에 웨이팅 및 주문 사용 불가 여부")
            .attributes(constraints("true 불가 | false 허용")),
        fieldWithPath("option.useWaiting")
            .type(JsonFieldType.BOOLEAN)
            .description("웨이팅 기능 사용 여부")
            .attributes(constraints("true 사용 | false 미사용")),
        fieldWithPath("option.useOrder")
            .type(JsonFieldType.BOOLEAN)
            .description("주문 기능 사용 여부")
            .attributes(constraints("true 사용 | false 미사용"))
    );
    public static final Snippet UPDATE_REQUEST = requestFields(
        fieldWithPath("id")
            .type(JsonFieldType.NUMBER)
            .description("매장 ID"),
        fieldWithPath("name")
            .type(JsonFieldType.STRING)
            .description("매장명")
            .attributes(constraints("50자 이하")),
        fieldWithPath("landlineNumber")
            .type(JsonFieldType.STRING)
            .description("매장 전화번호")
            .attributes(constraints("ex) 0551234567")),
        fieldWithPath("breakTimes[]")
            .type(JsonFieldType.ARRAY)
            .description("브레이크 타임 시작 시간")
            .optional(),
        fieldWithPath("breakTimes[].start")
            .type(JsonFieldType.STRING)
            .description("브레이크 타임 시작 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("breakTimes[].end")
            .type(JsonFieldType.STRING)
            .description("브레이크 타임 종료 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("breakTimes[].daysOfWeek")
            .type(JsonFieldType.ARRAY)
            .description("브레이크 타임 요일")
            .attributes(constraints("MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY")),
        fieldWithPath("businessTimes[].open")
            .type(JsonFieldType.STRING)
            .description("영업 시작 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("businessTimes[].close")
            .type(JsonFieldType.STRING)
            .description("영업 종료 시간")
            .attributes(constraints("HH:mm")),
        fieldWithPath("businessTimes[].daysOfWeek")
            .type(JsonFieldType.ARRAY)
            .description("영업 요일")
            .attributes(constraints("MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY"))
    );
    public static final Snippet UPDATE_OPTION_REQUEST = requestFields(
        fieldWithPath("storeId")
            .type(JsonFieldType.NUMBER)
            .description("매장 ID"),
        fieldWithPath("useBreakTime")
            .type(JsonFieldType.BOOLEAN)
            .description("브레이크 타임에 웨이팅 및 주문 사용 불가 여부")
            .attributes(constraints("true 불가 | false 허용")),
        fieldWithPath("useWaiting")
            .type(JsonFieldType.BOOLEAN)
            .description("웨이팅 기능 사용 여부")
            .attributes(constraints("true 사용 | false 미사용")),
        fieldWithPath("useOrder")
            .type(JsonFieldType.BOOLEAN)
            .description("주문 기능 사용 여부")
            .attributes(constraints("true 사용 | false 미사용"))
    );
}
