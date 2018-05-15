package com.eucolus.poll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String polls(Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println(referer);
        if(referer != null) {
            request.getSession().setAttribute("url_login_redirect", referer);
        }
        return "redirect:login/google";
    }
}
