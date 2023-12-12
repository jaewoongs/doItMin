package com.doitmin.webapp.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class SignUpController {
    @GetMapping
    public String mainPage(Model model) {
        return "login"; // src/main/resources/templates/index.html
    }
}