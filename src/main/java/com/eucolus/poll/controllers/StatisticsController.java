package com.eucolus.poll.controllers;

import com.eucolus.poll.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {
    @Autowired
    RequestRepository requestRepository;

    @RequestMapping("/statistics")
    public String statistics(Model model) {
        model.addAttribute("requests_count", requestRepository.findAllCount());
        model.addAttribute("dateCount", requestRepository.getDateHist());
        return "statistics";
    }
}
