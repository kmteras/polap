package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.PollUser;
import com.eucolus.poll.entities.RequestBrowser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    @Query(value = "SELECT * FROM polls WHERE creator_user_id=:#{#user.id}", nativeQuery = true)
    List<Poll> findByOwner(@Param("user") PollUser user);
}
