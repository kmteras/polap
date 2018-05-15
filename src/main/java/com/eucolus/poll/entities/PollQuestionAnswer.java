package com.eucolus.poll.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "poll_question_answers")
public class PollQuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String answer;
    private Timestamp creationDate;
    private Timestamp modificationDate;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_poll_answers_poll_question"))
    private PollQuestion question;
    private Boolean correct;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_user_id", foreignKey = @ForeignKey(name = "fk_poll_answers_creator_user"))
    private PollUser creatorUser;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modifying_user_id", foreignKey = @ForeignKey(name = "fk_poll_answers_modifying_user"))
    private PollUser modifyingUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public PollQuestion getQuestion() {
        return question;
    }

    public void setQuestion(PollQuestion question) {
        this.question = question;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public PollUser getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(PollUser creatorUser) {
        this.creatorUser = creatorUser;
    }

    public PollUser getModifyingUser() {
        return modifyingUser;
    }

    public void setModifyingUser(PollUser modifyingUser) {
        this.modifyingUser = modifyingUser;
    }
}
