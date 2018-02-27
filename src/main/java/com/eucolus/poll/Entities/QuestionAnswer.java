package com.eucolus.poll.Entities;

import javax.persistence.*;

@Entity
@Table(name="question_answers")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
}
