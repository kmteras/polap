package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollSession;
import org.springframework.data.repository.CrudRepository;

public interface PollSessionRepository extends CrudRepository<PollSession, Integer> {
}
