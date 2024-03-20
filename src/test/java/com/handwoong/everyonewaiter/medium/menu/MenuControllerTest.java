package com.handwoong.everyonewaiter.medium.menu;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuRequestSnippet.CREATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.menu.controller.request.MenuCreateRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuOptionGroupRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuSingleSelectOptionRequest;
import com.handwoong.everyonewaiter.menu.domain.MenuLabel;
import com.handwoong.everyonewaiter.menu.domain.MenuStatus;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/store.sql", "classpath:sql/category.sql", "classpath:sql/menu.sql"})
class MenuControllerTest extends TestHelper {

	@Test
	void Should_Create_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final MenuCreateRequest request = new MenuCreateRequest(
				2L,
				1L,
				"수비드 소고기 스테이크",
				"부채살을 수비드 방식으로 조리하여 촉촉하고 부드러운 식감을 즐길수 있는 스테이크",
				"",
				29_900,
				0,
				true,
				MenuStatus.BASIC,
				MenuLabel.REPRESENT,
				List.of(
						new MenuOptionGroupRequest(
								"맵기 조절",
								false,
								List.of(
										new MenuSingleSelectOptionRequest("안맵게", 0, false),
										new MenuSingleSelectOptionRequest("기본", 0, true)
								),
								List.of()
						)
				)
		);

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	private ExtractableResponse<Response> create(final String token, final MenuCreateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.filter(getFilter().document(AUTHORIZATION_HEADER, CREATE_REQUEST, CUD_RESPONSE))
				.when().post("/api/menus")
				.then().log().all().extract();
	}
}
