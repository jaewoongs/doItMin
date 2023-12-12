package com.doitmin.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String board(Model model) {
        model.addAttribute("no", 1234567);
        return "index"; // src/main/resources/templates/index.html
    }
}
