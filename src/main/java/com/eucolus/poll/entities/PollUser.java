package com.eucolus.poll.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class PollUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String googleUid;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modifyingUser", foreignKey = @ForeignKey(name = "fk_polls_modifying_user"))
    private List<Poll> modifiedPolls;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creatorUser", foreignKey = @ForeignKey(name = "fk_polls_creator_user"))
    private List<Poll> createdPolls;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modifyingUser", foreignKey = @ForeignKey(name = "fk_poll_questions_modifying_user"))
    private List<PollQuestion> modifiedPollQuestions;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creatorUser", foreignKey = @ForeignKey(name = "fk_poll_questions_creator_user"))
    private List<PollQuestion> createdPollQuestions;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "modifyingUser", foreignKey = @ForeignKey(name = "fk_poll_answers_modifying_user"))
    private List<PollQuestion> modifiedPollAnswers;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "creatorUser", foreignKey = @ForeignKey(name = "fk_poll_answers_creator_user"))
    private List<PollQuestion> createdPollAnswers;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user", foreignKey = @ForeignKey(name = "fk_user_answers_user"))
    private List<UserAnswers> userAnswers;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user", foreignKey = @ForeignKey(name = "fk_requests_user"))
    private List<Request> requests;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGoogleUid() {
        return googleUid;
    }

    public void setGoogleUid(String googleUid) {
        this.googleUid = googleUid;
    }
}
