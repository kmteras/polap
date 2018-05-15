package com.eucolus.poll.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "poll_questions")
public class PollQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String question;
    private Boolean multipleChoice;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<PollQuestionAnswer> questionAnswers;
    @JsonIgnore
    @ManyToOne
    private Poll poll;
    @ManyToOne
    private PollUser creatorUser;
    @ManyToOne
    private PollUser modifyingUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(Boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public List<PollQuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<PollQuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
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
