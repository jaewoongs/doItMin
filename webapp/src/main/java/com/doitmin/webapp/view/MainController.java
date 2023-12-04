package com.doitmin.webapp.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class MainController {
    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("no", 123);
        return "main"; // src/main/resources/templates/index.html
    }
}