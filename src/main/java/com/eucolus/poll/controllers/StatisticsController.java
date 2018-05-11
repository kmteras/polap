package com.eucolus.poll.controllers;

import com.eucolus.poll.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class StatisticsController {
    @Autowired
    RequestRepository requestRepository;

    @RequestMapping("/statistics")
    public String statistics(Model model, Principal user) {
        model.addAttribute("requests_count", requestRepository.findAllCount());
        model.addAttribute("dateCount", requestRepository.getDateHist());
        return "statistics";
    }
}
