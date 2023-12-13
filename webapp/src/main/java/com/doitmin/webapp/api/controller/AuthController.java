package com.doitmin.webapp.api.controller;


import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.dto.SignInDto;
import com.doitmin.webapp.api.dto.SignUpDto;
import com.doitmin.webapp.api.mapper.UserMapper;
import com.doitmin.webapp.api.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(authService.getProfile((ProfileDto) authentication.getPrincipal()));
    }

    @PostMapping("signup")
    public ResponseEntity<AuthTokenDto> signup(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(authService.signUp(UserMapper.INSTANCE.toEntity(signUpDto)));
    }

    @PostMapping("signin")
    public ResponseEntity<AuthTokenDto> signin(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(authService.signIn(signInDto.getEmail(), signInDto.getPassword()));
    }

    @GetMapping("refresh")
    public ResponseEntity<AuthTokenDto> refresh(@Parameter String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
}
