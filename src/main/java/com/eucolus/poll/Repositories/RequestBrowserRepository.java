package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.RequestBrowser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestBrowserRepository extends CrudRepository<RequestBrowser, Long> {
    @Query(value = "SELECT * FROM request_browsers WHERE name=(:name) AND version=(:version)", nativeQuery = true)
    RequestBrowser find(@Param("name") String name, @Param("version") String version);
}
