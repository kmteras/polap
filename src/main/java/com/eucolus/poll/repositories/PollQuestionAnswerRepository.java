package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollQuestionAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollQuestionAnswerRepository extends CrudRepository<PollQuestionAnswer, Integer> {
    @Query(value = "SELECT * FROM v_poll_question_answers WHERE question_id=(:questionId)", nativeQuery = true)
    List<PollQuestionAnswer> findByQuestionId(@Param("questionId") Integer questionId);
}
