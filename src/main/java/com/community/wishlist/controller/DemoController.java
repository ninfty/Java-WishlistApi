package com.community.wishlist.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {
    @GetMapping
    public String getTest(HttpServletRequest request) {
        return "CSRF Token: " + ((CsrfToken)request.getAttribute("_csrf")).getToken();
    }

    @PostMapping
    public String postTest() {
        return "POST: Hello world!";
    }

    @PutMapping("/white")
    public String putTest() {
        return "PUT: Hello world!";
    }

    @DeleteMapping
    public String deleteTest() {
        return "DELETE: Hello world!";
    }
}
