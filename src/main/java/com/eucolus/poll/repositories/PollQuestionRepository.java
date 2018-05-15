package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollQuestion;
import com.eucolus.poll.entities.RequestBrowser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollQuestionRepository extends CrudRepository<PollQuestion, Integer> {
    @Query(value = "SELECT * FROM v_poll_questions WHERE poll_id=(:pollId)", nativeQuery = true)
    List<PollQuestion> findByPollId(@Param("pollId") Integer pollId);
}
