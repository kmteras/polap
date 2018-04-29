package com.eucolus.poll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SitemapController {

    @GetMapping("/sitemap")
    public String sitemap(Model model, Principal user) {
        if(user != null)
            model.addAttribute("user", user.getName());
        else
            model.addAttribute("user", null);
        
        return "sitemap";
    }
}
