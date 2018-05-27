package com.eucolus.poll.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "location_ip", foreignKey = @ForeignKey(name = "fk_requests_location"))
    private RequestLocation location;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "os_id", foreignKey = @ForeignKey(name = "fk_requests_os"))
    private RequestOS os;
    private String requestType;
    private String requestUrl;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "browser_id", foreignKey = @ForeignKey(name = "fk_requests_browser"))
    private RequestBrowser browser;
    private Timestamp dateTime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_requests_user"))
    private PollUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RequestOS getOs() {
        return os;
    }

    public void setOs(RequestOS os) {
        this.os = os;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public RequestBrowser getBrowser() {
        return browser;
    }

    public void setBrowser(RequestBrowser browser) {
        this.browser = browser;
    }

    public RequestLocation getLocation() {
        return location;
    }

    public void setLocation(RequestLocation location) {
        this.location = location;
    }

    public PollUser getUser() {
        return user;
    }

    public void setUser(PollUser user) {
        this.user = user;
    }
}
