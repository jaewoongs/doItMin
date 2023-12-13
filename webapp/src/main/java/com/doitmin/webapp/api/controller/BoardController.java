package com.doitmin.webapp.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시판 API")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
    @GetMapping("")
    public ResponseEntity<String> board() {
        return ResponseEntity.badRequest().build();
    }
}
