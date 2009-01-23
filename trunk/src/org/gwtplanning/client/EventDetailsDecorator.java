// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.gwtplanning.client;

import org.gwtplanning.client.i18n.PlanningMessages;
import org.gwtplanning.client.model.Event;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
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

        private static DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd H:mm");

        public void decorate(Event event, Panel panel) {
            panel.setWidth(250);
            panel.setHeight(150);
            panel.add(new Label(messages.eventDetails_startDate(dateTimeFormat.format(event.getStart()))));
            panel.add(new Label(messages.eventDetails_endDate(dateTimeFormat.format(event.getEnd()))));
            long elapsedTime = (event.getEnd().getTime() - event.getStart().getTime()) / 1000 / 60;
            panel.add(new Label(messages.eventDetails_elapsedTime(elapsedTime + "")));
            // panel.add(new Label("Status: " + event.getStatus()));
        }
    }
}
