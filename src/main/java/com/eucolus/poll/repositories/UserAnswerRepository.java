package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.UserAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAnswerRepository extends CrudRepository<UserAnswer, Integer> {

    @Query(value = "SELECT * FROM user_answers WHERE answer_id=(:answerId) AND spring_session_code=(:springSession)", nativeQuery = true)
    UserAnswer getBySessionCode(@Param("answerId") int answerId, @Param("springSession") String springSession);

    @Query(value = "SELECT answer_id, COUNT(answer_id) FROM user_answers WHERE session_id = (:sessionId) AND checked=true GROUP BY answer_id", nativeQuery = true)
    List<Object[]> getSelectedPercentage(@Param("sessionId") int sessionId);

    @Query(value = "SELECT answer_id, COUNT(answer_id) FROM user_answers WHERE session_id = (:sessionId) GROUP BY answer_id", nativeQuery = true)
    List<Object[]> getTotalPercentage(@Param("sessionId") int sessionId);
}
