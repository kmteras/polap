package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollQuestionAnswer;
import org.springframework.data.repository.CrudRepository;

public interface PollQuestionAnswerRepository extends CrudRepository<PollQuestionAnswer, Integer> {
    
}
