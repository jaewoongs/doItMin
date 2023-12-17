package com.doitmin.webapp.api.controller;


import com.doitmin.webapp.api.dto.*;
import com.doitmin.webapp.api.mapper.UserMapper;
import com.doitmin.webapp.api.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "로그인 API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(authService.getProfile((ProfileDto) authentication.getPrincipal()));
    }

    @PostMapping(value = "signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenDto> signup(
            @Parameter(
                    required = false,
                    description = "key = profileImage",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart("profileImage") MultipartFile profileImage,
            @Parameter(description = "Sign Up DTO as JSON string", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SignUpDto.class)))
            @RequestPart("signUpDto") String signUpDtoJson) throws IOException {
        SignUpDto signUpDto = convertJsonStringToDto(signUpDtoJson);
        return ResponseEntity.ok(authService.signUp(UserMapper.INSTANCE.toEntity(signUpDto), profileImage));
    }

    @PostMapping("signin")
    public ResponseEntity<AuthTokenDto> signin(@Valid @RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(authService.signIn(signInDto.getEmail(), signInDto.getPassword()));
    }

    @GetMapping("refresh")
    public ResponseEntity<AuthTokenDto> refresh(@Parameter String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }
    private SignUpDto convertJsonStringToDto(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, SignUpDto.class);
        } catch (IOException e) {
            // Handle the exception, possibly throw a custom exception
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}
