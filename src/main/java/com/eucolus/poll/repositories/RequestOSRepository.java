package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.RequestOS;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RequestOSRepository extends CrudRepository<RequestOS, Integer> {
    @Query(value = "SELECT * FROM v_request_oss", nativeQuery = true)
    List<RequestOS> findAll();

    @Query(value = "SELECT * FROM v_request_oss WHERE os_name=(:osName) AND os_group=(:osGroup)", nativeQuery = true)
    RequestOS find(@Param("osName") String osName, @Param("osGroup") String osGroup);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO request_oss (os_name, os_group) VALUES (:#{#os.name}, :#{#os.group})", nativeQuery = true)
    Integer add(@Param("os") RequestOS os);
}
