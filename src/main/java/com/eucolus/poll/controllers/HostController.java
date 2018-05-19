package com.eucolus.poll.controllers;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class HostController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("/host/{pollId}")
    public String poll(@PathVariable(value="pollId") Integer pollId, Model model, Principal user) {
        model.addAttribute("poll", pollRepository.findOne(pollId));
        return "host";
    }
}
