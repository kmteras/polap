package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Question;
import com.eucolus.poll.Entities.QuestionSummary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Query(value = "SELECT * from questions", nativeQuery = true)
    List<QuestionSummary> findAllQuestions();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO questions (text) VALUES(:text)", nativeQuery = true)
    void addQuestion(@Param("text") String text);
}
