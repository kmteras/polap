package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.UserAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, Integer> {

    @Query(value = "SELECT * FROM user_answers WHERE answer_id=(:answerId) AND spring_session_code=(:springSession)", nativeQuery = true)
    UserAnswer getBySessionCode(@Param("answerId") int answerId, @Param("springSession") String springSession);
}
