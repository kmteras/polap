package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.Poll;
import com.eucolus.poll.entities.RequestBrowser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PollRepository extends CrudRepository<Poll, Integer> {

}
