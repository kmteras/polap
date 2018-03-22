package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollQuestion;
import org.springframework.data.repository.CrudRepository;

public interface PollQuestionRepository extends CrudRepository<PollQuestion, Integer> {
    
}
