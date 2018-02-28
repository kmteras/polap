package com.eucolus.poll.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_locations")
public class RequestLocation {
    @Id
    private String ip;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
}
