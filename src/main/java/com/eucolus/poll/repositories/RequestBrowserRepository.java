package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.RequestBrowser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestBrowserRepository extends CrudRepository<RequestBrowser, Integer> {
    @Query(value = "SELECT * FROM v_request_browsers WHERE name=(:name) AND version=(:version)", nativeQuery = true)
    RequestBrowser find(@Param("name") String name, @Param("version") String version);
}
