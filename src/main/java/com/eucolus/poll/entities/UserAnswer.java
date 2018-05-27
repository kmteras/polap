package com.eucolus.poll.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "session_id", foreignKey = @ForeignKey(name = "fk_user_answers_poll_session"))
    private PollSession session;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "answer_id", foreignKey = @ForeignKey(name = "fk_user_answers_poll_answer"))
    private PollQuestionAnswer answer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_answers_user"))
    private PollUser user;

    private boolean checked;

    private Timestamp time;

    public String springSessionCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PollSession getSession() {
        return session;
    }

    public void setSession(PollSession session) {
        this.session = session;
    }

    public PollQuestionAnswer getAnswer() {
        return answer;
    }

    public void setAnswer(PollQuestionAnswer answer) {
        this.answer = answer;
    }

    public PollUser getUser() {
        return user;
    }

    public void setUser(PollUser user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSpringSessionCode() {
        return springSessionCode;
    }

    public void setSpringSessionCode(String springSessionCode) {
        this.springSessionCode = springSessionCode;
    }
}
