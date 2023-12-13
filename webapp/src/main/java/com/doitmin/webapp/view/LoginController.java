package com.doitmin.webapp.view;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("permitAll")
@RequestMapping("/signup")
public class LoginController {
    @GetMapping
    public String mainPage(Model model) {
        return "signup"; // src/main/resources/templates/index.html
    }
}