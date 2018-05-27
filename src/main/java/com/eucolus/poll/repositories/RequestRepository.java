package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    @Query(value = "SELECT * FROM v_only_main_requests", nativeQuery = true)
    List<Request> findAll();

    @Query(value = "SELECT * FROM v_only_main_requests_count", nativeQuery = true)
    Integer findAllCount();

    @Query(value = "SELECT * FROM v_date_history", nativeQuery = true)
    List<Object> getDateHist();

    @Query(value = "SELECT * FROM v_browser_request_count", nativeQuery = true)
    List<Object[]> getBrowserRequestsCount();

    @Query(value = "SELECT * FROM v_os_request_count;", nativeQuery = true)
    List<Object> getOSRequestsCount();
}
