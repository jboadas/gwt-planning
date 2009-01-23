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
package org.gwtplanning.client.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gwtplanning.client.model.Category;
import org.gwtplanning.client.model.CategoryImpl;
import org.gwtplanning.client.model.Event;
import org.gwtplanning.client.model.EventImpl;

import com.google.gwt.user.client.Random;

/**
 * Random implementation of PlanningDataProvider. Generates random data.
 */
public class RandomPlanningDataProvider implements LocalPlanningDataProvider {

    private int nbCategories;

    public RandomPlanningDataProvider(int nbCategories) {
        super();
        this.nbCategories = nbCategories;
    }

    public Category[] getEvents(Date startDate, Date endDate) {
        List<Category> toReturn = new ArrayList<Category>();

        for (int i = 1; i <= nbCategories; i++) {
            CategoryImpl category = new CategoryImpl("Task " + i);
            toReturn.add(category);

            List<Event> events = new ArrayList<Event>();

            Date currentEndDate = startDate;
            while (currentEndDate.before(endDate)) {
                EventImpl event = new EventImpl();

                event.setStart(new Date(currentEndDate.getTime() + Random.nextInt(12 * 60 * 60 * 1000)));
                event.setEnd(new Date(event.getStart().getTime() + Random.nextInt(180 * 60 * 1000)));

                currentEndDate = event.getEnd();

                int nextInt = Random.nextInt(3);
                switch (nextInt) {
                case 0:
                    event.setStatus("log-ok");
                    break;
                case 1:
                    event.setStatus("log-warning");
                    break;
                default:
                    event.setStatus("log-error");
                    break;
                }

                events.add(event);
            }
            category.setEvents(events);
        }

        return toReturn.toArray(new Category[] {});
    }

}
