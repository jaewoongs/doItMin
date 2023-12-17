package com.doitmin.webapp.view;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String mainPage(Model model) {
        return "login";
    }

    @PostMapping
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password, 
                              HttpServletResponse response,
                                Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/auth/signin"; // Replace with your actual API

        try {
            LoginRequest loginRequest = new LoginRequest(email, password);
            ResponseEntity<String> apiResponse = restTemplate.postForEntity(url, loginRequest, String.class);
             String responseBody = apiResponse.getBody();

             ObjectMapper objectMapper = new ObjectMapper();
             AuthResponse authResponse = objectMapper.readValue(responseBody, AuthResponse.class);
             String refreshToken = authResponse.getRefreshToken();

             // 쿠키 생성 및 설정
             Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
             refreshCookie.setPath("/"); // 쿠키 접근 경로 설정
             refreshCookie.setMaxAge(60 * 60 * 24 * 7); // 1주일

             response.addCookie(refreshCookie);

            //  Access Token
             String accessToken = authResponse.getAccessToken();
             model.addAttribute("accessToken", accessToken);
             // Profile API 호출
            String profileUrl = "http://localhost:8080/api/v1/auth/profile";
            ResponseEntity<String> profileResponse = restTemplate.exchange(
                profileUrl, HttpMethod.GET, new HttpEntity<>(createHeaders(authResponse.getAccessToken())), String.class);
            ProfileResponse userProfile = objectMapper.readValue(profileResponse.getBody(), ProfileResponse.class);

            // 모델에 사용자 프로필 추가
            model.addAttribute("userProfile", userProfile);

             return "loginSuccess";
        } catch (Exception e) {
            model.addAttribute("error", "이메일 또는 비밀번호를 확인해주세요.");
            return "login";
        }
    }
    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getter and setter for email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Getter and setter for password
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    public static class AuthResponse {
        private String accessToken;
        private String refreshToken;
    
        public AuthResponse() {}
    
        public String getAccessToken() {
            return accessToken;
        }
    
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    
        public String getRefreshToken() {
            return refreshToken;
        }
    
        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
    public static class ProfileResponse {
        private String id;
        private String email;
        private String nickname;
        private String[] roleNames;
    
        public ProfileResponse() {}
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public String getNickname() {
            return nickname;
        }
    
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    
        public String[] getRoleNames() {
            return roleNames;
        }
    
        public void setRoleNames(String[] roleNames) {
            this.roleNames = roleNames;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
    }
}