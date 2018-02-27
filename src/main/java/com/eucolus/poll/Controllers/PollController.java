package com.eucolus.poll.Controllers;

import com.eucolus.poll.Repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping(value = "")
    public String polls(Model model) {
        model.addAttribute("polls", pollRepository.findAllPolls());
        return "polls";
    }

    @GetMapping(value = "/{pollId}")
    public String poll(@PathVariable(value="pollId") Integer pollId, Model model) {
        model.addAttribute("poll", pollRepository.findPollWithId(pollId));
        return "poll";
    }
}
