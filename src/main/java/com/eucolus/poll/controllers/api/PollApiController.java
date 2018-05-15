package com.eucolus.poll.controllers.api;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollQuestion;
import com.eucolus.poll.entities.PollQuestionAnswer;
import com.eucolus.poll.entities.PollUser;
import com.eucolus.poll.repositories.PollQuestionAnswerRepository;
import com.eucolus.poll.repositories.PollQuestionRepository;
import com.eucolus.poll.repositories.PollRepository;
import com.eucolus.poll.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Autowired
    private UserService userService;

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
    HttpStatus setPoll(@PathVariable(value="pollId") Integer pollId, @RequestBody Poll poll, Principal principal) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        poll.setModificationDate(currentTime);
        if(principal == null) {
            return HttpStatus.FORBIDDEN;
        }
        PollUser user = userService.getUser(principal);

        poll.setModifyingUser(user);
        pollRepository.save(poll);
        List<PollQuestion> previousQuestionList = questionRepository.findByPollId(pollId);
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
            if(question.getCreationDate() == null) {
                question.setCreationDate(currentTime);
            }

            if(question.getCreatorUser() == null) {
                question.setCreatorUser(user);
            }

            question.setModificationDate(currentTime);
            question.setModifyingUser(user);

            questionRepository.save(question);
            List<PollQuestionAnswer> questionAnswers = answerRepository.findByQuestionId(question.getId());
            for(int j = 0; j < questionAnswers.size(); j++) {
                PollQuestionAnswer answer = questionAnswers.get(j);
                answer.setQuestion(question);

                if(answer.getCreationDate() == null) {
                    answer.setCreationDate(currentTime);
                }

                if(answer.getCreatorUser() == null) {
                    answer.setCreatorUser(user);
                }

                answer.setModificationDate(currentTime);
                answer.setModifyingUser(user);

                answerRepository.save(answer);
            }
        }
        questionRepository.delete(previousQuestionList);
        return HttpStatus.OK;
    }
}
