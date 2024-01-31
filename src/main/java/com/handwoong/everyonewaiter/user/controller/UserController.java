package com.handwoong.everyonewaiter.user.controller;

import com.handwoong.everyonewaiter.common.dto.ApiResponse;
import com.handwoong.everyonewaiter.common.infrastructure.jwt.JwtToken;
import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
import com.handwoong.everyonewaiter.user.controller.request.UserLoginRequest;
import com.handwoong.everyonewaiter.user.domain.UserId;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> join(@RequestBody @Valid final UserJoinRequest request) {
        final UserId userId = userService.join(request.toDomainDto());
        return ResponseEntity
            .created(URI.create(userId.toString()))
            .body(ApiResponse.success());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtToken>> login(@RequestBody @Valid final UserLoginRequest request) {
        final JwtToken accessToken = userService.login(request.toDomainDto());
        return ResponseEntity.ok(ApiResponse.success(accessToken));
    }
}
