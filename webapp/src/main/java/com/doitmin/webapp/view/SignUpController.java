package com.doitmin.webapp.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @GetMapping
    public String mainPage(Model model) {
        // Existing code
        return "signup";
    }

    @PostMapping
    public String handleSignup(@RequestParam String email,
        @RequestParam String password,
        @RequestParam String confirmPassword,
        @RequestParam String nickname,
        Model model, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "비밀번호 비밀번호 확인이 다릅니다.");
            return "signup";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/auth/signup";

        try {
            SignupRequest signupRequest = new SignupRequest(email, password, nickname);
            restTemplate.postForEntity(url, signupRequest, String.class);

            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/signup";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "signup";
        }
    }

    static class SignupRequest {
        private String email;
        private String password;
        private String nickname;

        public SignupRequest(String email, String password, String nickname) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
        }

        // Getters
        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getNickname() {
            return nickname;
        }

        // Setters
        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}