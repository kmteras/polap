package com.eucolus.poll.controllers;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollSession;
import com.eucolus.poll.entities.PollUser;
import com.eucolus.poll.repositories.PollRepository;
import com.eucolus.poll.repositories.PollSessionRepository;
import com.eucolus.poll.services.PollService;
import com.eucolus.poll.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class SessionController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @GetMapping("/session-host/{pollId}")
    public String hostSession(@PathVariable(value="pollId") Integer pollId, Model model, Principal principal) {
        PollUser user = userService.getUser(principal);

        if(user == null) {
            return "redirect:/";
        }

        Poll poll = pollRepository.findOne(pollId);

        if(poll == null || poll.getCreatorUser() != userService.getUser(principal)) {
            return "redirect:/polls";
        }

        PollSession pollSession = pollService.startSession(poll, user);

        return "redirect:/session/" + pollSession.getCode();
    }

    @GetMapping("/session-end/{sessionCode}")
    public String endSession(@PathVariable(value="sessionCode") String sessionCode, Model model, Principal principal) {
        PollUser user = userService.getUser(principal);

        if(user == null) {
            return "redirect:/";
        }

        PollSession pollSession = pollService.findSession(sessionCode);

        if(pollSession == null) {
            return "redirect:/polls";
        }

        pollService.endSession(pollSession);

        return "redirect:/polls/";
    }

    @GetMapping("/session/{sessionCode}")
    public String session(@PathVariable(value="sessionCode") String sessionCode, Model model, Principal principal) {
        PollSession pollSession = pollService.findSession(sessionCode);

        if(pollSession == null) {
            return "redirect:/";
        }

        model.addAttribute("pollSession", pollSession);

        if(pollSession.getHost().equals(userService.getUser(principal))) {
            return "host";
        }

        Poll poll = pollSession.getPoll();

        model.addAttribute("poll", poll);
        return "answer";
    }
}
