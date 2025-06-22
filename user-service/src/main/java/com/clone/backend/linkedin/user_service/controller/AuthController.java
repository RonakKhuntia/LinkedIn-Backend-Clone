package com.clone.backend.linkedin.user_service.controller;

import com.clone.backend.linkedin.user_service.model.LoginDto;
import com.clone.backend.linkedin.user_service.model.SignupDto;
import com.clone.backend.linkedin.user_service.model.UserDto;
import com.clone.backend.linkedin.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto) {
        UserDto userDto = authService.signup(signupDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

}
