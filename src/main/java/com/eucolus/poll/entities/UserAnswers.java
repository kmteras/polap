package com.eucolus.poll.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_answers")
public class UserAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private PollQuestionAnswer answer;

    @ManyToOne
    private PollUser user;

    private Timestamp time;
}
