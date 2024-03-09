package com.handwoong.everyonewaiter.medium.category;

import static com.handwoong.everyonewaiter.category.domain.CategoryIcon.CATEGORY_ICON_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryIcon.CATEGORY_ICON_MAX_LENGTH_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryName.CATEGORY_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.category.domain.CategoryName.CATEGORY_NAME_MAX_LENGTH_MESSAGE;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.category.snippet.CategoryRequestSnippet.CREATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.category.snippet.CategoryRequestSnippet.QUERY_PARAM_STORE_ID;
import static com.handwoong.everyonewaiter.medium.category.snippet.CategoryRequestSnippet.UPDATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.category.snippet.CategoryResponseSnippet.CATEGORIES_RESPONSE;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.category.controller.request.CategoryCreateRequest;
import com.handwoong.everyonewaiter.category.controller.request.CategoryUpdateRequest;
import com.handwoong.everyonewaiter.category.controller.response.CategoryResponses;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/store.sql", "classpath:sql/category.sql"})
class CategoryControllerTest extends TestHelper {

	@Test
	void Should_ThreeCategories_When_FindStoreCategories() {
		// given
		// when
		final ExtractableResponse<Response> response = categories();
		final TypeRef<ApiResponse<CategoryResponses>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<CategoryResponses> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.data().categories()).hasSize(3);
	}

	private ExtractableResponse<Response> categories() {
		return RestAssured
				.given(getSpecification()).log().all()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(QUERY_PARAM_STORE_ID, CATEGORIES_RESPONSE))
				.queryParam("store", 2L)
				.when().get("/api/categories/list")
				.then().log().all().extract();
	}

	@Test
	void Should_Create_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크", "drumstick");

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_CreateStatus400_When_CategoryNameBlank(final String categoryName) {
		// given
		final String token = userAccessToken;
		final CategoryCreateRequest request = new CategoryCreateRequest(1L, categoryName, "drumstick");

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_NAME_EMPTY_MESSAGE);
	}

	@Test
	void Should_CreateStatus400_When_CategoryNameLengthGreaterThan20() {
		// given
		final String token = userAccessToken;
		final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크".repeat(6), "drumstick");

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_NAME_MAX_LENGTH_MESSAGE);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_CreateStatus400_When_CategoryIconBlank(final String icon) {
		// given
		final String token = userAccessToken;
		final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크", icon);

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_ICON_EMPTY_MESSAGE);
	}

	@Test
	void Should_CreateStatus400_When_CategoryIconLengthGreaterThan20() {
		// given
		final String token = userAccessToken;
		final CategoryCreateRequest request = new CategoryCreateRequest(1L, "스테이크", "drumstick".repeat(4));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_ICON_MAX_LENGTH_MESSAGE);
	}

	private ExtractableResponse<Response> create(final String token, final CategoryCreateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.filter(getFilter().document(AUTHORIZATION_HEADER, CREATE_REQUEST, CUD_RESPONSE))
				.when().post("/api/categories")
				.then().log().all().extract();
	}

	@Test
	void Should_Update_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, "파스타", "utensils");

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
		assertThat(result.resultCode()).isEqualTo(ResultCode.SUCCESS);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_UpdateStatus400_When_CategoryNameBlank(final String categoryName) {
		// given
		final String token = userAccessToken;
		final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, categoryName, "utensils");

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_NAME_EMPTY_MESSAGE);
	}

	@Test
	void Should_UpdateStatus400_When_CategoryNameLengthGreaterThan20() {
		// given
		final String token = userAccessToken;
		final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, "스테이크".repeat(6), "drumstick");

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_NAME_MAX_LENGTH_MESSAGE);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_UpdateStatus400_When_CategoryIconBlank(final String icon) {
		// given
		final String token = userAccessToken;
		final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, "스테이크", icon);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_ICON_EMPTY_MESSAGE);
	}

	@Test
	void Should_UpdateStatus400_When_CategoryIconLengthGreaterThan20() {
		// given
		final String token = userAccessToken;
		final CategoryUpdateRequest request = new CategoryUpdateRequest(1L, 2L, "스테이크", "drumstick".repeat(4));

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(CATEGORY_ICON_MAX_LENGTH_MESSAGE);
	}

	private ExtractableResponse<Response> update(final String token, final CategoryUpdateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.filter(getFilter().document(AUTHORIZATION_HEADER, UPDATE_REQUEST, CUD_RESPONSE))
				.when().put("/api/categories")
				.then().log().all().extract();
	}
}
