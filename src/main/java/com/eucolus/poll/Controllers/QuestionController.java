package com.eucolus.poll.Controllers;

import com.eucolus.poll.Entities.QuestionEntity;
import com.eucolus.poll.Entities.QuestionSummary;
import com.eucolus.poll.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path="/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(value = "/")
    public String questionPage(Model model) {
        model.addAttribute("questionsList", questionRepository.findAllQuestions());
        model.addAttribute("question", new QuestionEntity());
        return "questions";
    }

    @PostMapping("/api")
    public @ResponseBody String addQuestion(@RequestParam String text) {
        QuestionEntity question = new QuestionEntity();
        question.setText(text);
        questionRepository.save(question);
        return "Saved";
    }

    @PostMapping("/api/add")
    public @ResponseBody String addQuestionForm(@Valid @ModelAttribute("question") QuestionEntity question,
                                                BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            return "Error";
        }

        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setText(question.getText());
        questionRepository.save(questionEntity);

        return "Success?";
    }

    @GetMapping("/api")
    public @ResponseBody String welcome() {
        List<QuestionSummary> qs = questionRepository.findAllQuestions();

        return qs.toString();
    }
}
