package com.eucolus.poll.Controllers;

import com.eucolus.poll.Entities.Question;
import com.eucolus.poll.Entities.QuestionAnswer;
import com.eucolus.poll.Repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping(value = "")
    public String questionPage(Model model) {
        model.addAttribute("questionsList", questionRepository.findAllQuestions());
        System.out.println(questionRepository.findAllQuestions());
        model.addAttribute("question", new Question());
        model.addAttribute("answer", new QuestionAnswer());
        return "questions";
    }

    @PostMapping("/form")
    public @ResponseBody
    String addQuestionForm(@Valid @ModelAttribute("question") Question question,
                           @Valid @ModelAttribute("answer") QuestionAnswer answer,
                           BindingResult result, ModelMap model) {
        if(result.hasErrors()) {
            return "Error";
        }

        System.out.println(answer);

        questionRepository.addQuestion(question.getText());

        return "Success?";
    }
}
