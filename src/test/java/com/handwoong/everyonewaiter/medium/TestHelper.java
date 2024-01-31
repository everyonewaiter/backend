package com.handwoong.everyonewaiter.medium;

import static com.handwoong.everyonewaiter.medium.RestDocsUtils.getSpecification;
import static com.handwoong.everyonewaiter.medium.RestDocsUtils.setSpecification;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import com.handwoong.everyonewaiter.user.service.port.UserRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class TestHelper {

    public static String userAccessToken;
    public static String adminAccessToken;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private static String getToken(final Username username, final Password password) {
        final UserLoginRequest request = new UserLoginRequest(username.toString(), password.toString());
        final ExtractableResponse<Response> response = login(request);
        final TypeRef<ApiResponse<JwtToken>> typeRef = new TypeRef<>() {
        };
        final ApiResponse<JwtToken> result = response.body().as(typeRef);
        return result.data().token();
    }

    private static ExtractableResponse<Response> login(final UserLoginRequest request) {
        return RestAssured
            .given(getSpecification())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/api/users/login")
            .then().extract();
    }

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        RestAssured.port = port;
        final RequestSpecification specification = new RequestSpecBuilder()
            .addFilter(
                documentationConfiguration(provider)
                    .operationPreprocessors()
                    .withRequestDefaults(RestDocsUtils.removeHeaders())
                    .withResponseDefaults(RestDocsUtils.removeHeaders())
            )
            .addFilter(RestDocsUtils.getFilter())
            .build();
        setSpecification(specification);

        final Password password = new Password("123456");
        final User user = User.builder()
            .username(new Username("handwoong"))
            .password(password.encode(passwordEncoder))
            .phoneNumber(new PhoneNumber("01012345678"))
            .role(UserRole.ROLE_USER)
            .status(UserStatus.ACTIVE)
            .build();
        final User admin = User.builder()
            .username(new Username("admin"))
            .password(password.encode(passwordEncoder))
            .phoneNumber(new PhoneNumber("01012345678"))
            .role(UserRole.ROLE_ADMIN)
            .status(UserStatus.ACTIVE)
            .build();
        userRepository.save(user);
        userRepository.save(admin);
        userAccessToken = getToken(user.getUsername(), password);
        adminAccessToken = getToken(admin.getUsername(), password);
    }

    @AfterEach
    void clear() {
        databaseCleaner.execute();
    }
}
