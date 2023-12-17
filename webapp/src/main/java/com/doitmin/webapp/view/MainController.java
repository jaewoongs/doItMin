package com.doitmin.webapp.view;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String mainPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/board/list";
        try {
            ResponseEntity<List<Map<String, Object>>> response = 
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {});

            List<Map<String, Object>> boards = response.getBody();
            model.addAttribute("boards", boards);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index"; 
    }

    static public class BoardRequest {
    private double latitude;
    private double longitude;

    public BoardRequest(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

}