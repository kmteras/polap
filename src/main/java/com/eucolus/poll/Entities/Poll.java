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
}
