package com.eucolus.poll.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/polls")
public class PollController {

    @GetMapping(value = "")
    public String polls(Model model) {
        return "polls";
    }
}
