package com.eucolus.poll.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "request_locations")
public class RequestLocation {
    @Id
    @NotNull
    private String ip;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;
    @JsonIgnore
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "location", foreignKey = @ForeignKey(name = "fk_requests_location"))
    private List<Request> requests;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public boolean isSet() {
        return !(city == null || country == null || latitude == null || longitude == null);
    }
}
