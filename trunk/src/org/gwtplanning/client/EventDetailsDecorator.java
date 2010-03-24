package org.gwtplanning.client;

import org.gwtplanning.client.i18n.PlanningMessages;
import org.gwtplanning.client.model.Event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.widgets.Panel;

/**
 * DOC stephane class global comment. Detailled comment
 */
public interface EventDetailsDecorator {

    public void decorate(Event event, Panel panel);

    /**
     * DOC stephane class global comment. Detailled comment
     */
    public class EventDetailsDecoratorImpl implements EventDetailsDecorator {

        private static PlanningMessages messages = (PlanningMessages) GWT.create(PlanningMessages.class);

        public void decorate(Event event, Panel panel) {
            panel.setWidth(250);
            panel.setHeight(150);
            panel.add(new Label(messages.eventDetails_startDate(Constants.DATE_FORMAT.format(event.getStart()))));
            panel.add(new Label(messages.eventDetails_endDate(Constants.DATE_FORMAT.format(event.getEnd()))));
            long elapsedTime = (event.getEnd().getTime() - event.getStart().getTime()) / 1000 / 60;
            panel.add(new Label(messages.eventDetails_elapsedTime(elapsedTime + "")));
            // panel.add(new Label("Status: " + event.getStatus()));
        }
    }
}
