package com.eucolus.poll.Controllers.Api;

import com.eucolus.poll.Repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/api/questions")
public class RequestAPIController {
    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("")
    public @ResponseBody String getAllRequests() {
        return requestRepository.findAllRequests().toString();
    }
}
