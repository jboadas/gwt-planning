package org.gwtplanning.client.model;

import java.util.Date;

/**
 * Default implementation of Event interface.
 */
public class EventImpl implements Event {

    private Date start;

    private Date end;

    private String status;

    public Date getStart() {
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
