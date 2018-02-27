package com.eucolus.poll.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @OneToMany(targetEntity = Question.class)
    private List<Question> questions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
