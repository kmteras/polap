package com.eucolus.poll.Repositories;

import com.eucolus.poll.Entities.RequestBrowser;
import com.eucolus.poll.Entities.RequestLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestLocationRepository extends CrudRepository<RequestLocation, Long> {
    @Query(value = "SELECT * FROM request_locations WHERE ip=(:ip)", nativeQuery = true)
    RequestLocation find(@Param("ip") String ip);
}
