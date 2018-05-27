package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.*;
import com.eucolus.poll.repositories.PollQuestionAnswerRepository;
import com.eucolus.poll.repositories.PollRepository;
import com.eucolus.poll.repositories.UserAnswerRepository;
import com.eucolus.poll.services.PollService;
import com.eucolus.poll.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping(path="/api/sessions")
public class SessionApiController {

    @Autowired
    private PollService pollService;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private PollQuestionAnswerRepository pollQuestionAnswerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @GetMapping("/poll/{sessionCode}")
    public @ResponseBody
    String poll(@PathVariable(value="sessionCode") String sessionCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        Poll poll = pollRepository.findOne(pollService.getPoll(sessionCode).getId());
        String springSession = RequestContextHolder.currentRequestAttributes().getSessionId();

        String pollString = "";

        try {
            pollString = objectMapper.writeValueAsString(poll);

            JSONObject jsonObject = new JSONObject(pollString);
            JSONArray questionArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject question = questionArray.getJSONObject(i);

                JSONArray answerArray = question.getJSONArray("questionAnswers");

                for (int j = 0; j < answerArray.length(); j++) {
                    JSONObject answer = answerArray.getJSONObject(j);

                    int answerId = answer.getInt("id");

                    PollQuestionAnswer pollQuestionAnswer = pollQuestionAnswerRepository.findOne(answerId);

                    UserAnswer userAnswer = userAnswerRepository.getBySessionCode(answerId, springSession);

                    Boolean isCorrect = pollQuestionAnswer.getCorrect();

                    if(isCorrect == null) {
                        isCorrect = false;
                    }

                    if(userAnswer != null) {
                        answer.put("checked", userAnswer.isCorrect() == isCorrect);
                    }
                }
            }
            pollString = jsonObject.toString();
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return pollString;
    }

    @PostMapping("/answer/{sessionCode}")
    public @ResponseBody
    void answer(@PathVariable(value="sessionCode") String sessionCode, @RequestBody String poll, Principal principal) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        String springSession = RequestContextHolder.currentRequestAttributes().getSessionId();

        System.out.println();

        PollUser pollUser = userService.getUser(principal);

        PollSession pollSession = pollService.getSavedSession(sessionCode);

        JSONObject jsonObject = new JSONObject(poll);
        JSONArray questionArray = jsonObject.getJSONArray("questions");

        for (int i = 0; i < questionArray.length(); i++) {
            JSONObject question = questionArray.getJSONObject(i);

            JSONArray answerArray = question.getJSONArray("questionAnswers");

            for (int j = 0; j < answerArray.length(); j++) {
                JSONObject answer = answerArray.getJSONObject(j);

                int answerId = answer.getInt("id");

                PollQuestionAnswer pollQuestionAnswer = pollQuestionAnswerRepository.findOne(answerId);

                UserAnswer userAnswer = userAnswerRepository.getBySessionCode(answerId, springSession);

                if(userAnswer == null) {
                    userAnswer = new UserAnswer();
                }

                Boolean isCorrect = pollQuestionAnswer.getCorrect();

                if(isCorrect == null) {
                    isCorrect = false;
                }

                userAnswer.setCorrect(isCorrect.equals(answer.getBoolean("checked")));
                userAnswer.setSession(pollSession);
                userAnswer.setTime(currentTime);
                userAnswer.setAnswer(pollQuestionAnswer);

                userAnswer.setSpringSessionCode(springSession);
                userAnswer.setUser(pollUser);

                userAnswerRepository.save(userAnswer);
            }
        }
    }
}
