package com.eucolus.poll.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "poll_sessions")
public class PollSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "poll_id", foreignKey = @ForeignKey(name = "fk_poll_session_poll"))
    private Poll poll;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "host_id", foreignKey = @ForeignKey(name = "fk_poll_session_user"))
    private PollUser host;

    private String code;

    private Timestamp startTime;

    private Timestamp endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public PollUser getHost() {
        return host;
    }

    public void setHost(PollUser host) {
        this.host = host;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
