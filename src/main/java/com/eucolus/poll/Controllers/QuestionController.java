package com.eucolus.poll.Controllers;

import com.eucolus.poll.Entities.QuestionEntity;
import com.eucolus.poll.Entities.QuestionSummary;
import com.eucolus.poll.QuestionRepository;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/add")
    public @ResponseBody String addQuestion(@RequestParam String text) {
        QuestionEntity question = new QuestionEntity();
        question.setText(text);
        questionRepository.save(question);
        return "Saved";
    }

    @GetMapping("/")
    public @ResponseBody String welcome() {
        List<QuestionSummary> qs = questionRepository.findAllQuestions();

        return qs.toString();
    }
}
