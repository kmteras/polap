package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
