package com.community.wishlist.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {
    @GetMapping
    public String getTest() {
        return "GET : Hello world!";
    }

    @PostMapping
    public String postTest() {
        return "POST: Hello world!";
    }

    @PutMapping
    public String putTest() {
        return "PUT: Hello world!";
    }

    @DeleteMapping
    public String deleteTest() {
        return "DELETE: Hello world!";
    }
}
