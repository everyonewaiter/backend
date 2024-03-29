package com.handwoong.everyonewaiter.medium.menu;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuRequestSnippet.CREATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuRequestSnippet.PATH_PARAM_MENU_ID;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuRequestSnippet.QUERY_PARAM_STORE_ID;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuRequestSnippet.UPDATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.menu.snippet.MenuResponseSnippet.MENUS_RESPONSE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.menu.controller.request.MenuCreateRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuOptionGroupRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuSingleSelectOptionRequest;
import com.handwoong.everyonewaiter.menu.controller.request.MenuUpdateRequest;
import com.handwoong.everyonewaiter.menu.controller.response.MenuResponses;
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

	@Test
	void Should_Update_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final MenuUpdateRequest request = new MenuUpdateRequest(
				1L,
				2L,
				1L,
				"소고기 스테이크 (수비드)",
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
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	private ExtractableResponse<Response> update(final String token, final MenuUpdateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.filter(getFilter().document(AUTHORIZATION_HEADER, UPDATE_REQUEST, CUD_RESPONSE))
				.when().put("/api/menus")
				.then().log().all().extract();
	}

	@Test
	void Should_Delete_When_ValidRequest() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = delete(token);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	private ExtractableResponse<Response> delete(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_MENU_ID, CUD_RESPONSE))
				.when().delete("/api/menus/{id}", 1L)
				.then().log().all().extract();
	}

	@Test
	void Should_FindMenus_When_ValidStoreId() {
		// given
		// when
		final ExtractableResponse<Response> response = findAllByStore();
		final TypeRef<ApiResponse<MenuResponses>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<MenuResponses> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
		assertThat(result.data().menus()).hasSize(2);
	}

	private ExtractableResponse<Response> findAllByStore() {
		return RestAssured
				.given(getSpecification()).log().all()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(QUERY_PARAM_STORE_ID, MENUS_RESPONSE))
				.queryParam("store", 2L)
				.when().get("/api/menus/list")
				.then().log().all().extract();
	}
}
