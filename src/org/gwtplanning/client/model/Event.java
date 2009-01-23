package org.gwtplanning.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Events to be shown in planning.
 */
public interface Event extends Serializable {

    public Date getStart();

    public Date getEnd();

    public String getStatus();

}
