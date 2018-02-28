package com.eucolus.poll.Entities;

import javax.persistence.*;

@Entity
@Table(name = "request_oss",
        uniqueConstraints = @UniqueConstraint(columnNames = {"os_name", "os_group"}))
public class RequestOS {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "os_name")
    private String name;
    @Column(name = "os_group")
    private String group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
