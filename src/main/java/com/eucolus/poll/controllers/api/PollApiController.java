package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/api/polls")
public class PollApiController {
    @Autowired
    private PollRepository pollRepository;

    @PostMapping("/{pollId}/title")
    public @ResponseBody
    Boolean getDateHist(@PathVariable(value="pollId") Integer pollId, @RequestParam Map<String, String> params) {
        Poll poll = pollRepository.findOne(pollId);
        poll.setTitle(params.get("title"));
        pollRepository.save(poll);
        return true;
    }

    @DeleteMapping("/{pollId}")
    public @ResponseBody
    Boolean getDateHist(@PathVariable(value="pollId") Integer pollId) {
        Poll poll = pollRepository.findOne(pollId);

        if(poll != null) {
            pollRepository.delete(pollId);
        }
        return true;
    }
}
