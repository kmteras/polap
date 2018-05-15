package com.eucolus.poll.repositories;

import com.eucolus.poll.entities.RequestLocation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.zip.DeflaterOutputStream;

public interface RequestLocationRepository extends CrudRepository<RequestLocation, Integer> {
    @Query(value = "SELECT * FROM v_request_locations WHERE ip=(:ip)", nativeQuery = true)
    RequestLocation find(@Param("ip") String ip);

    @Modifying
    @Transactional
    @Query(value = "UPDATE request_locations " +
            "SET country=:country, " +
                "city=:city, " +
                "longitude=:longitude, " +
                "latitude=:latitude " +
            "WHERE ip=:ip", nativeQuery = true)
    Integer update(@Param("ip") String ip,
                           @Param("country") String country,
                           @Param("city") String city,
                           @Param("longitude") Double longitude,
                           @Param("latitude") Double latitude);

    @Modifying
    @Transactional
    @Query(value = "INSERT IGNORE INTO request_locations (ip) VALUES (:ip)", nativeQuery = true)
    Integer add(@Param("ip") String ip);
}
