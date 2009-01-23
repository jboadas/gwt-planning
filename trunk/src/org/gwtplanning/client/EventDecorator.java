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
