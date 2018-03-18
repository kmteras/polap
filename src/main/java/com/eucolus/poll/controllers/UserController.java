package com.eucolus.poll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/register")
    public String index() {
        return "index";
    }
}
