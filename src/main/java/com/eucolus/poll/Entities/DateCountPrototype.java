package com.eucolus.poll.Entities;

import java.sql.Date;
import java.sql.Timestamp;

public class DateCountPrototype {
    Timestamp dateTime;
    Integer count;

    public DateCountPrototype(Timestamp dateTime, Integer count) {
        this.dateTime = dateTime;
        this.count = count;
    }
}
