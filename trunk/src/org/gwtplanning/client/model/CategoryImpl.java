package org.gwtplanning.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of Category interface.
 */
public class CategoryImpl implements Category {

    private String name;

    private List<Event> events = new ArrayList<Event>();

    public CategoryImpl(String name) {
        super();
        this.name = name;
    }

    /**
     * DOC stephane CategoryImpl constructor comment.
     */
    public CategoryImpl() {
        super();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CategoryImpl other = (CategoryImpl) obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        } else if (!this.name.equals(other.name))
            return false;
        return true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
