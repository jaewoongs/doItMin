package com.doitmin.webapp.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String mainPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/"; // Replace with the actual API URL
        String responseData = restTemplate.getForObject(url, String.class);

        model.addAttribute("data", responseData);
        return "login";
    }
}