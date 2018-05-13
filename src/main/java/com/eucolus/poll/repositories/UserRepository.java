package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.PollUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<PollUser, Integer> {

    @Query(value = "SELECT * FROM users WHERE email=(:email)", nativeQuery = true)
    PollUser findByEmail(@Param("email") String email);
}
