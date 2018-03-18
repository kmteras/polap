package com.eucolus.poll.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class QuestionsController {

    @RequestMapping("/questions")
    public String questions(Model model, Principal user) {
        if(user != null)
            model.addAttribute("user", user.getName());
        else
            model.addAttribute("user", null);

        if(user == null)
            return "redirect:/";
        else
            return "questions";
    }
}
