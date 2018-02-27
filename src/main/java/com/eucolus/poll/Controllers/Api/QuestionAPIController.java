package com.eucolus.poll.Controllers.Api;

import com.eucolus.poll.Entities.Question;
import com.eucolus.poll.Entities.QuestionSummary;
import com.eucolus.poll.Repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/api/questions")
public class QuestionAPIController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public @ResponseBody String welcome() {
        List<QuestionSummary> qs = questionRepository.findAllQuestions();

        return qs.toString();
    }

    @PostMapping("/spring")
    public @ResponseBody String addQuestionSpring(@RequestParam String text) {
        Question question = new Question();
        question.setText(text);
        questionRepository.save(question);
        return "Saved";
    }

    @PostMapping("")
    public void addQuestion(@RequestParam String text) {
        questionRepository.addQuestion(text);
        System.out.println("Saved?");
    }
}
