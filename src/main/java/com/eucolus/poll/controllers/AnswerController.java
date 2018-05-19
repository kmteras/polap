package com.eucolus.poll.controllers;

import com.eucolus.poll.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class AnswerController {

    @Autowired
    private PollRepository pollRepository;

    // Just for development
    @GetMapping("/answer")
    public String poll(Model model, Principal user) {
        return "answer";
    }

    @GetMapping("/answer/{pollId}")
    public String poll(@PathVariable(value="pollId") Integer pollId, Model model, Principal user) {
        model.addAttribute("poll", pollRepository.findOne(pollId));
        return "answer";
    }
}
