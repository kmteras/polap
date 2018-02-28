package com.eucolus.poll.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RequestOS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String osName;
    private String osGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsGroup() {
        return osGroup;
    }

    public void setOsGroup(String osGroup) {
        this.osGroup = osGroup;
    }
}
