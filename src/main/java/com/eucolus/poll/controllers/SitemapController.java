package com.eucolus.poll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitemapController {

    @GetMapping("/sitemap")
    public String sitemap() {
        if(user != null)
            model.addAttribute("user", user.getName());
        else
            model.addAttribute("user", null);
        
        return "sitemap";
    }
}
