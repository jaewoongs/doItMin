package com.doitmin.webapp.api.controller;


import com.doitmin.webapp.api.dto.SignUpDto;
import com.doitmin.webapp.api.dto.AuthTokenDto;
import com.doitmin.webapp.api.entities.User;
import com.doitmin.webapp.api.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping("")
    public String board() {
        return "boardNew1231231234123412344";
    }

    @PostMapping("signup")
    public ResponseEntity<AuthTokenDto> signup(@Valid @RequestBody SignUpDto signUpDto){
        User user = new User();
        return ResponseEntity.ok(authService.signUp(user));
    }
}
