package com.eucolus.poll.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_answers")
public class UserAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id", foreignKey = @ForeignKey(name = "fk_user_answers_poll_answer"))
    private PollQuestionAnswer answer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_answers_user"))
    private PollUser user;

    private Timestamp time;
}
