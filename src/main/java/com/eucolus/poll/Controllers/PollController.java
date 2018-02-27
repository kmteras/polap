package com.eucolus.poll.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/polls")
public class PollController {

    @GetMapping(value = "")
    public String polls(Model model) {
        return "polls";
    }

    @GetMapping(value = "/{pollId}")
    public String poll(@PathVariable(value="pollId") Integer pollId, Model model) {
        model.addAttribute("pollId", pollId);
        System.out.println("Test");
        return "poll";
    }
}
