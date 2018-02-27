package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Poll;
import com.eucolus.poll.Entities.Question;
import com.eucolus.poll.Entities.QuestionSummary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Long> {
    @Query(value = "SELECT * text from polls", nativeQuery = true)
    List<Poll> findAllPolls();
}
