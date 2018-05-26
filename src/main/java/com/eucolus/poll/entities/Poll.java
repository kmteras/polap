package com.eucolus.poll.entities;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "polls")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;

    @JsonIgnore
    private Timestamp creationDate;

    @JsonIgnore
    private Timestamp modificationDate;

    @OneToMany(mappedBy = "poll")
    private List<PollQuestion> questions;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creator_user_id", foreignKey = @ForeignKey(name = "fk_polls_creator_user"))
    private PollUser creatorUser;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modifying_user_id", foreignKey = @ForeignKey(name = "fk_polls_modifying_user"))
    private PollUser modifyingUser;

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

    @JsonIgnore
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    @JsonIgnore
    public PollUser getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(PollUser creatorUser) {
        this.creatorUser = creatorUser;
    }

    @JsonIgnore
    public PollUser getModifyingUser() {
        return modifyingUser;
    }

    public void setModifyingUser(PollUser modifyingUser) {
        this.modifyingUser = modifyingUser;
    }

    public List<PollQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PollQuestion> questions) {
        this.questions = questions;
    }
}
