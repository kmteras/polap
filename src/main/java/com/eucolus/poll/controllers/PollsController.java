package com.eucolus.poll.controllers;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.repositories.PollRepository;
import com.eucolus.poll.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PollsController {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/polls")
    public String polls(Model model, Principal user) {
        if(user == null) {
            return "login";
        }

        model.addAttribute("polls", pollRepository.findAll());
        return "polls";
    }

    @GetMapping("/create_poll")
    public String poll(Model model, Principal user) {
        Poll newPoll = new Poll();
        newPoll.setTitle("Title");
        newPoll.setCreationDate(new Timestamp(System.currentTimeMillis()));
        newPoll.setCreatorUser(userService.getUser(user));
        pollRepository.save(newPoll);

        model.addAttribute("poll", newPoll);
        return "questions";
    }

    @GetMapping("/polls/{pollId}")
    public String poll(@PathVariable(value="pollId") Integer pollId, Model model, Principal user) {
        Poll poll = pollRepository.findOne(pollId);

        if(userService.getUser(user) != poll.getCreatorUser()) {
            return "redirect:/login";
        }

        model.addAttribute("poll", pollRepository.findOne(pollId));
        return "questions";
    }
}
