package com.zerobase.reservation.controller;


import com.zerobase.reservation.dto.UserDto;
import com.zerobase.reservation.security.TokenProvider;
import com.zerobase.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto request) {
        UserDto user = userService.register(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDto request) {
        UserDto user = userService.authenticate(request);
        String token = tokenProvider
                .generateToken(user.getId(), user.getRoles());
        return ResponseEntity.ok(token);
    }
}
