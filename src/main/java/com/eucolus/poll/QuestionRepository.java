package com.eucolus.poll;

import com.eucolus.poll.Entities.QuestionEntity;
import com.eucolus.poll.Entities.QuestionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
    @Query(value = "SELECT text from question_entity", nativeQuery = true)
    List<QuestionSummary> findAllQuestions();
}
