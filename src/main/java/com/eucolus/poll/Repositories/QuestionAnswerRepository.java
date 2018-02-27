package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.QuestionAnswer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO question_answers (text) VALUES(:text)", nativeQuery = true)
    void addAnswer(@Param("text") String text);
}
