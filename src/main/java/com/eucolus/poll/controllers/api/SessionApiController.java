package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.*;
import com.eucolus.poll.repositories.PollQuestionAnswerRepository;
import com.eucolus.poll.repositories.PollRepository;
import com.eucolus.poll.repositories.UserAnswerRepository;
import com.eucolus.poll.services.PollService;
import com.eucolus.poll.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.groovy.util.HashCodeHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigInteger;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/statistics/{sessionCode}")
    public @ResponseBody
    String poll(@PathVariable(value="sessionCode") String sessionCode, Principal principal) {
        PollSession pollSession = pollService.findSession(sessionCode);

        int pollSessionId = pollSession.getId();

        PollUser pollUser = userService.getUser(principal);

        if(!pollSession.getHost().equals(pollUser)) {
            return "";
        }


        Poll poll = pollRepository.findOne(pollSession.getPoll().getId());

        List<PollQuestion> pollQuestionList = poll.getQuestions();

        List<Object[]> userAnswerPercentages = userAnswerRepository.getSelectedPercentage(pollSessionId);
        List<Object[]> userTotalPercentages = userAnswerRepository.getTotalPercentage(pollSessionId);

        Map<Integer, Integer> userSelectedPercentageMap = new HashMap<>();
        Map<Integer, Integer> userPercentageMap = new HashMap<>();

        for(int i = 0; i < userAnswerPercentages.size(); i++) {
            Integer answerId = (Integer)userAnswerPercentages.get(i)[0];
            BigInteger count = (BigInteger)userAnswerPercentages.get(i)[1];
            userSelectedPercentageMap.put(answerId, count.intValue());
            System.out.println(answerId + " " + count);
        }

        System.out.println();

        for(int i = 0; i < userTotalPercentages.size(); i++) {
            Integer answerId = (Integer)userTotalPercentages.get(i)[0];
            BigInteger count = (BigInteger)userTotalPercentages.get(i)[1];
            userPercentageMap.put(answerId, count.intValue());
            System.out.println(answerId + " " + count);
        }

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < pollQuestionList.size(); i++) {
            PollQuestion pollQuestion = pollQuestionList.get(i);

            List<PollQuestionAnswer> pollQuestionAnswers = pollQuestion.getQuestionAnswers();

            JSONObject jsonQuestion = new JSONObject();

            jsonQuestion.put("id", pollQuestion.getId());
            jsonQuestion.put("question", pollQuestion.getQuestion());

            JSONArray answerArray = new JSONArray();

            for(int j = 0; j < pollQuestionAnswers.size(); j++) {
                JSONObject jsonAnswer = new JSONObject();
                PollQuestionAnswer pollQuestionAnswer = pollQuestionAnswers.get(j);

                int selected = userSelectedPercentageMap.getOrDefault(pollQuestionAnswer.getId(), 0);

                Double percentage = (double)selected /
                        (double)userPercentageMap.get(pollQuestionAnswer.getId()) * 100;

                jsonAnswer.put("id", pollQuestionAnswer.getId());
                jsonAnswer.put("answer", pollQuestionAnswer.getAnswer());
                jsonAnswer.put("correct", pollQuestionAnswer.getCorrect());

                jsonAnswer.put("selected", selected);
                jsonAnswer.put("percentage", Math.round(percentage * 100.0)/100.0);

                answerArray.put(jsonAnswer);

                System.out.println(percentage);
            }

            jsonQuestion.put("answers", answerArray);

            jsonArray.put(jsonQuestion);
        }

        jsonObject.put("statistics", jsonArray);

        return jsonObject.toString();
    }

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

                    UserAnswer userAnswer = userAnswerRepository.getBySessionCode(answerId, springSession);

                    if(userAnswer != null) {
                        answer.put("checked", userAnswer.isChecked());
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

                userAnswer.setChecked(answer.getBoolean("checked"));
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
