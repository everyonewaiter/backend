package com.handwoong.everyonewaiter.medium.store;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getFilter;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_KEY;
import static com.handwoong.everyonewaiter.medium.common.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER_TYPE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreRequestSnippet.CREATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreRequestSnippet.PATH_PARAM_STORE_ID;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreRequestSnippet.UPDATE_OPTION_REQUEST;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreRequestSnippet.UPDATE_REQUEST;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.CUD_RESPONSE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.STORE_RESPONSE;
import static com.handwoong.everyonewaiter.medium.store.snippet.StoreResponseSnippet.STORE_RESPONSES;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.FRIDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SATURDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.SUNDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.THURSDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.TUESDAY;
import static com.handwoong.everyonewaiter.store.domain.DayOfWeek.WEDNESDAY;
import static com.handwoong.everyonewaiter.store.domain.LandlineNumber.LANDLINE_NUMBER_FORMAT_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_EMPTY_MESSAGE;
import static com.handwoong.everyonewaiter.store.domain.StoreName.STORE_NAME_MAX_LENGTH_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.dto.ApiResponse.ResultCode;
import com.handwoong.everyonewaiter.medium.TestHelper;
import com.handwoong.everyonewaiter.store.controller.request.StoreBreakTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreBusinessTimeRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateOptionRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreCreateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreOptionUpdateRequest;
import com.handwoong.everyonewaiter.store.controller.request.StoreUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/store.sql"})
class StoreControllerTest extends TestHelper {

	private final List<StoreBusinessTimeRequest> businessTimes = List.of(
			new StoreBusinessTimeRequest(
					LocalTime.of(11, 0, 0),
					LocalTime.of(21, 0, 0),
					List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
			)
	);
	private final List<StoreBreakTimeRequest> breakTimes = List.of(
			new StoreBreakTimeRequest(
					LocalTime.of(15, 0, 0),
					LocalTime.of(16, 30, 0),
					List.of(TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
			),
			new StoreBreakTimeRequest(
					LocalTime.of(15, 30, 0),
					LocalTime.of(17, 0, 0),
					List.of(SATURDAY, SUNDAY)
			)
	);

	@Test
	void Should_StoreResponses_When_LoginUser() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = findAllByUser(token);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	@Test
	void Should_FindAllByUserStatus401_When_LogoutUser() {
		// given
		final String token = "";

		// when
		final ExtractableResponse<Response> response = findAllByUser(token);

		// then
		assertThat(response.statusCode()).isEqualTo(401);
	}

	private ExtractableResponse<Response> findAllByUser(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, STORE_RESPONSES))
				.when().get("/api/stores")
				.then().log().all().extract();
	}

	@Test
	void Should_StoreResponse_When_ValidRequest() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = findByUser(token, 1L);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	@Test
	void Should_FindByUserStatus401_When_LogoutUser() {
		// given
		final String token = "";

		// when
		final ExtractableResponse<Response> response = findByUser(token, 1L);

		// then
		assertThat(response.statusCode()).isEqualTo(401);
	}

	@Test
	void Should_FindByUserStatus404_When_StoreNotFound() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = findByUser(token, 100L);

		// then
		assertThat(response.statusCode()).isEqualTo(404);
	}

	private ExtractableResponse<Response> findByUser(final String token, final Long storeId) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_STORE_ID, STORE_RESPONSE))
				.when().get("/api/stores/{id}", storeId)
				.then().log().all().extract();
	}

	@Test
	void Should_Create_When_ValidRequest() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루", "0551234567", breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);

		// then
		assertThat(response.statusCode()).isEqualTo(201);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_CreateStatus400_When_StoreNameBlank(final String storeName) throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				storeName, "0551234567", breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(STORE_NAME_EMPTY_MESSAGE);
	}

	@Test
	void Should_CreateStatus400_When_StoreNameLengthGreaterThan50() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루 레스토랑".repeat(8), "0551234567", breakTimes, businessTimes,
				new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(STORE_NAME_MAX_LENGTH_MESSAGE);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_CreateStatus400_When_LandlineNumberBlank(final String landlineNumber) throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루", landlineNumber, breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
	}

	@Test
	void Should_CreateStatus400_When_InvalidLandlineNumberFormat() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루", "055-123-4567", breakTimes, businessTimes, new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
	}

	@Test
	void Should_CreateStatus400_When_BreakTimesDayOfWeekEmpty() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루", "0551234567", List.of(
				new StoreBreakTimeRequest(
						LocalTime.of(15, 0, 0),
						LocalTime.of(16, 30, 0),
						List.of()
				)
		), businessTimes, new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("브레이크 타임의 요일을 하나 이상 등록해주세요.");
	}

	@Test
	void Should_CreateStatus400_When_BusinessTimesDayOfWeekEmpty() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreCreateRequest request = new StoreCreateRequest(
				"나루", "0551234567", breakTimes, List.of(
				new StoreBusinessTimeRequest(
						LocalTime.of(11, 0, 0),
						LocalTime.of(21, 0, 0),
						List.of()
				)
		), new StoreCreateOptionRequest(true, true, true));

		// when
		final ExtractableResponse<Response> response = create(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("영업 시간의 요일을 하나 이상 등록해주세요.");
	}

	private ExtractableResponse<Response> create(
			final String token,
			final StoreCreateRequest request
	) throws Exception {
		final String body = objectMapper.writeValueAsString(request);
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(body)
				.filter(getFilter().document(AUTHORIZATION_HEADER, CREATE_REQUEST, CUD_RESPONSE))
				.when().post("/api/stores")
				.then().log().all().extract();
	}

	@Test
	void Should_Update_When_ValidRequest() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, "나루 레스토랑", "021234567", breakTimes, businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_UpdateStatus400_When_StoreNameBlank(final String storeName) throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, storeName, "021234567", breakTimes, businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(STORE_NAME_EMPTY_MESSAGE);
	}

	@Test
	void Should_UpdateStatus400_When_StoreNameLengthGreaterThan50() throws Exception {
		// given
		final String token = userAccessToken;
		final String storeName = "나루 레스토랑".repeat(8);
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, storeName, "021234567", breakTimes, businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(STORE_NAME_MAX_LENGTH_MESSAGE);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void Should_UpdateStatus400_When_LandlineNumberBlank(final String landlineNumber) throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, "나루 레스토랑", landlineNumber, breakTimes, businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
	}

	@Test
	void Should_UpdateStatus400_When_InvalidLandlineNumberFormat() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, "나루 레스토랑", "055-123-4567", breakTimes, businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo(LANDLINE_NUMBER_FORMAT_MESSAGE);
	}

	@Test
	void Should_UpdateStatus400_When_BreakTimesDayOfWeekEmpty() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, "나루 레스토랑", "021234567", List.of(
				new StoreBreakTimeRequest(
						LocalTime.of(15, 0, 0),
						LocalTime.of(16, 30, 0),
						List.of()
				)
		), businessTimes);

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("브레이크 타임의 요일을 하나 이상 등록해주세요.");
	}

	@Test
	void Should_UpdateStatus400_When_BusinessTimesDayOfWeekEmpty() throws Exception {
		// given
		final String token = userAccessToken;
		final StoreUpdateRequest request = new StoreUpdateRequest(
				1L, "나루 레스토랑", "021234567", breakTimes, List.of(
				new StoreBusinessTimeRequest(
						LocalTime.of(11, 0, 0),
						LocalTime.of(21, 0, 0),
						List.of()
				)
		));

		// when
		final ExtractableResponse<Response> response = update(token, request);
		final TypeRef<ApiResponse<Void>> typeRef = new TypeRef<>() {
		};
		final ApiResponse<Void> result = response.body().as(typeRef);

		// then
		assertThat(response.statusCode()).isEqualTo(400);
		assertThat(result.resultCode()).isEqualTo(ResultCode.FAIL);
		assertThat(result.message()).isEqualTo("영업 시간의 요일을 하나 이상 등록해주세요.");
	}

	private ExtractableResponse<Response> update(
			final String token,
			final StoreUpdateRequest request
	) throws Exception {
		final String body = objectMapper.writeValueAsString(request);
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(body)
				.filter(getFilter().document(AUTHORIZATION_HEADER, UPDATE_REQUEST, CUD_RESPONSE))
				.when().put("/api/stores")
				.then().log().all().extract();
	}

	@Test
	void Should_UpdateOption_When_ValidRequest() {
		// given
		final String token = userAccessToken;
		final StoreOptionUpdateRequest request = new StoreOptionUpdateRequest(1L, false, false, false);

		// when
		final ExtractableResponse<Response> response = update(token, request);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	private ExtractableResponse<Response> update(final String token, final StoreOptionUpdateRequest request) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(request)
				.filter(getFilter().document(AUTHORIZATION_HEADER, UPDATE_OPTION_REQUEST, CUD_RESPONSE))
				.when().put("/api/stores/option")
				.then().log().all().extract();
	}

	@Test
	void Should_DeleteStore_When_ValidRequest() {
		// given
		final String token = userAccessToken;

		// when
		final ExtractableResponse<Response> response = delete(token);

		// then
		assertThat(response.statusCode()).isEqualTo(200);
	}

	private ExtractableResponse<Response> delete(final String token) {
		return RestAssured
				.given(getSpecification()).log().all()
				.header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.filter(getFilter().document(AUTHORIZATION_HEADER, PATH_PARAM_STORE_ID, CUD_RESPONSE))
				.when().delete("/api/stores/{id}", 1L)
				.then().log().all().extract();
	}
}
