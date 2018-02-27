package com.eucolus.poll.Entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    @OneToMany(targetEntity = QuestionAnswer.class)
    private List<QuestionAnswer> answers;
    private Boolean multibleChoice = false;
    @OneToMany(targetEntity = QuestionAnswer.class)
    private List<QuestionAnswer> rightAnswers;

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

    public List<QuestionAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswer> answers) {
        this.answers = answers;
    }

    public boolean isMultibleChoice() {
        return multibleChoice;
    }

    public void setMultibleChoice(boolean multibleChoice) {
        this.multibleChoice = multibleChoice;
    }

    public List<QuestionAnswer> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<QuestionAnswer> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
}
