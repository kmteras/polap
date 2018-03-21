package com.eucolus.poll.entities;

import javax.persistence.*;

@Entity
@Table(name = "poll_question_answers")
public class PollQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    @ManyToOne
    private PollQuestion question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PollQuestion getQuestion() {
        return question;
    }

    public void setQuestion(PollQuestion question) {
        this.question = question;
    }
}
