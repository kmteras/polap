package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    @Query(value = "SELECT * FROM requests", nativeQuery = true)
    List<Request> findAll();

    @Query(value = "SELECT COUNT(*) FROM requests", nativeQuery = true)
    Integer findAllCount();

    @Query(value = "SELECT COUNT(DISTINCT location_ip) FROM requests", nativeQuery = true)
    Integer findAllDistinctCount();

    @Query(value = "SELECT FROM_UNIXTIME(ROUND(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60)) AS datetime, " +
            "COUNT(id) AS count " +
            "FROM requests GROUP BY FROM_UNIXTIME(ROUND(UNIX_TIMESTAMP(date_time)/(60*60))*(60*60))", nativeQuery = true)
    List<Object> getDateHist();

    @Query(value = "SELECT name, COUNT(name) FROM requests JOIN request_browsers " +
            "WHERE request_browsers.id = requests.browser_id GROUP BY name", nativeQuery = true)
    List<Object> getBrowserRequestsCount();

    @Query(value = "SELECT CONCAT(os_name, ' ', os_group), COUNT(os_name) FROM requests JOIN request_oss WHERE request_oss.id = requests.os_id GROUP BY os_name, os_group;", nativeQuery = true)
    List<Object> getOSRequestsCount();
}
