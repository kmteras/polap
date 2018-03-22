package com.eucolus.poll.controllers.api;

import com.eucolus.poll.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/api/polls")
public class PollApiController {
    @Autowired
    private PollRepository pollRepository;

    @PostMapping("/{pollId}/title")
    public @ResponseBody
    Boolean getDateHist(@PathVariable(value="pollId") Integer pollId, @RequestBody String title) {
        System.out.println(title);
        return true;
    }
}
