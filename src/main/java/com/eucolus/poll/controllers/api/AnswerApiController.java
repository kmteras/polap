package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollQuestion;
import com.eucolus.poll.entities.PollQuestionAnswer;
import com.eucolus.poll.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path="/api/answers")
public class AnswerApiController {

    @Autowired
    private PollService pollSErvice;

    @PostMapping("/{sessionCode}")
    public @ResponseBody
    void answer(@PathVariable(value="sessionCode") String sessionCode, @RequestBody Poll poll, Principal principal) {

        System.out.println(poll.getTitle());
        List<PollQuestion> currentQuestionList = poll.getQuestions();

        for(int i = 0; i < currentQuestionList.size(); i++) {
            PollQuestion question = currentQuestionList.get(i);

            System.out.println(question.getQuestion());

            List<PollQuestionAnswer> questionAnswers = question.getQuestionAnswers();

            for(int j = 0; j < questionAnswers.size(); j++) {
                PollQuestionAnswer answer = questionAnswers.get(j);

                System.out.println(answer.getAnswer() + " " + answer.getCorrect());
            }
        }
    }
}
