package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.DateCountPrototype;
import com.eucolus.poll.Entities.Request;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.sql.Timestamp;
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
}
