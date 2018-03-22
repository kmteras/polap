package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Integer> {

}
