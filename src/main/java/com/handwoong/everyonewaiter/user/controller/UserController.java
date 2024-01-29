package com.handwoong.everyonewaiter.user.controller;

import com.handwoong.everyonewaiter.user.controller.port.UserService;
import com.handwoong.everyonewaiter.user.controller.request.UserJoinRequest;
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
    public ResponseEntity<Void> join(@RequestBody @Valid final UserJoinRequest request) {
        final Long userId = userService.join(request.toDomainDto());
        return ResponseEntity.created(URI.create(userId.toString())).build();
    }
}
