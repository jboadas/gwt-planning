package org.gwtplanning.client;

import org.gwtplanning.client.model.Event;

import com.google.gwt.user.client.ui.SimplePanel;

/**
 * DOC stephane class global comment. Detailled comment
 */
public interface EventDecorator {

    public void decorate(Event event, SimplePanel panel, boolean active);

    /**
     * DOC stephane class global comment. Detailled comment
     */
    public class EventDecoratorImpl implements EventDecorator {

        public void decorate(Event event, SimplePanel panel, boolean active) {
            panel.setStylePrimaryName("planning-" + event.getStatus() + (active ? "-focus" : ""));
        }
    }
}
