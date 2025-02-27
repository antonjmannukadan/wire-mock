package com.example.wiremock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DemoController {
    @Autowired
    private  RestTemplate restTemplate;

    @Value("${external.api.base.url}")
    private String baseUrl;



    @GetMapping("/quote")
    public String getQuote() {
        try {
            String url = baseUrl + "/quotes/1";
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "External service error", e);
        }
    }
}
