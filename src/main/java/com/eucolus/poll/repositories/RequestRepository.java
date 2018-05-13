package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Integer> {
    @Query(value = "SELECT * FROM only_main_requests", nativeQuery = true)
    List<Request> findAll();

    @Query(value = "SELECT COUNT(*) FROM only_main_requests", nativeQuery = true)
    Integer findAllCount();

    @Query(value = "SELECT COUNT(DISTINCT location_ip) FROM only_main_requests", nativeQuery = true)
    Integer findAllDistinctCount();

    @Query(value = "SELECT FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60)) AS datetime, " +
            "COUNT(id) AS count " +
            "FROM only_main_requests GROUP BY FROM_UNIXTIME(FLOOR(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60))", nativeQuery = true)
    List<Object> getDateHist();

    @Query(value = "SELECT name, COUNT(name) FROM only_main_requests JOIN request_browsers " +
            "WHERE request_browsers.id = only_main_requests.browser_id GROUP BY name", nativeQuery = true)
    List<Object[]> getBrowserRequestsCount();

    @Query(value = "SELECT os_group, COUNT(os_name) FROM only_main_requests JOIN request_oss WHERE request_oss.id = only_main_requests.os_id GROUP BY os_name, os_group;", nativeQuery = true)
    List<Object> getOSRequestsCount();
}
