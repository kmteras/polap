package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Request;
import com.eucolus.poll.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
