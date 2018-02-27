package com.eucolus.poll.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String ip;
    private String os;
    private String requestType;
    private String requestUrl;
    private String browser;
    private Timestamp dateTime;
}
