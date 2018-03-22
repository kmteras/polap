package com.eucolus.poll.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "poll_questions")
public class PollQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    private Boolean multipleChoice;
    @OneToMany(mappedBy = "question")
    private List<PollQuestionAnswer> questionAnswers;
    @ManyToOne
    private Poll poll;

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
}
