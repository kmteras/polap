package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Request;
import com.eucolus.poll.Entities.RequestOS;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    @Query(value = "SELECT * FROM requests", nativeQuery = true)
    List<Request> findAllRequests();
}
