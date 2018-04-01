package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollQuestion;
import com.eucolus.poll.entities.PollQuestionAnswer;
import com.eucolus.poll.repositories.PollQuestionAnswerRepository;
import com.eucolus.poll.repositories.PollQuestionRepository;
import com.eucolus.poll.repositories.PollRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/api/polls")
public class PollApiController {
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private PollQuestionRepository questionRepository;
    @Autowired
    private PollQuestionAnswerRepository answerRepository;

    @PostMapping("/{pollId}/title")
    public @ResponseBody
    void setTitle(@PathVariable(value="pollId") Integer pollId, @RequestParam Map<String, String> params) {
        Poll poll = pollRepository.findOne(pollId);
        poll.setTitle(params.get("title"));
        pollRepository.save(poll);
    }

    @DeleteMapping("/{pollId}")
    public @ResponseBody
    void deletePost(@PathVariable(value="pollId") Integer pollId) {
        Poll poll = pollRepository.findOne(pollId);

        if(poll != null) {
            pollRepository.delete(pollId);
        }
    }

    @GetMapping("/{pollId}")
    public @ResponseBody
    Poll getPoll(@PathVariable(value="pollId") Integer pollId) {
        return pollRepository.findOne(pollId);
    }

    @PostMapping(value="/{pollId}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    HttpStatus setPoll(@PathVariable(value="pollId") Integer pollId, @RequestBody Poll poll) {
        poll.setModificationDate(new Timestamp(System.currentTimeMillis()));
        pollRepository.save(poll);
        List<PollQuestion> previousQuestionList = questionRepository.find(pollId);
        List<PollQuestion> currentQuestionList = poll.getQuestions();

        for(int i = 0; i < currentQuestionList.size(); i++) {
            PollQuestion question = currentQuestionList.get(i);
            for(int j = 0; j < previousQuestionList.size(); j++) {
                PollQuestion previousQuestion = previousQuestionList.get(j);
                if(previousQuestion.getId().equals(question.getId())) {
                    previousQuestionList.remove(j);
                    break;
                }
            }

            question.setPoll(poll);
            questionRepository.save(question);
            List<PollQuestionAnswer> questionAnswers = question.getQuestionAnswers();
            for(int j = 0; j < questionAnswers.size(); j++) {
                PollQuestionAnswer answer = questionAnswers.get(j);
                answer.setQuestion(question);
                answerRepository.save(answer);
            }
        }
        questionRepository.delete(previousQuestionList);
        return HttpStatus.OK;
    }
}
