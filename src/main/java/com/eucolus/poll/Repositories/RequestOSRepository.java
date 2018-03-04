package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.Request;
import com.eucolus.poll.Entities.RequestOS;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RequestOSRepository extends CrudRepository<RequestOS, Long> {
    @Query(value = "SELECT * FROM request_oss", nativeQuery = true)
    List<RequestOS> findAll();

    @Query(value = "SELECT * FROM request_oss WHERE os_name=(:osName) AND os_group=(:osGroup)", nativeQuery = true)
    RequestOS find(@Param("osName") String osName, @Param("osGroup") String osGroup);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO request_oss (os_name, os_group) VALUES (:#{#os.name}, :#{#os.group})", nativeQuery = true)
    Integer add(@Param("os") RequestOS os);
}
