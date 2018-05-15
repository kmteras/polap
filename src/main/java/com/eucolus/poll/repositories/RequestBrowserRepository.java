package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.RequestBrowser;
import com.eucolus.poll.entities.RequestOS;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RequestBrowserRepository extends CrudRepository<RequestBrowser, Integer> {
    @Query(value = "SELECT * FROM v_request_browsers WHERE name=(:name) AND version=(:version)", nativeQuery = true)
    RequestBrowser find(@Param("name") String name, @Param("version") String version);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO request_browsers (name, version) VALUES (:#{#browser.name}, :#{#browser.version})", nativeQuery = true)
    Integer add(@Param("browser") RequestBrowser browser);
}
