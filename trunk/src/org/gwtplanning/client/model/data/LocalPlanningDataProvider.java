package org.gwtplanning.client.model.data;

import java.util.Date;

import org.gwtplanning.client.model.Category;

/**
 * This class is used by the planning to retrieve data.
 */
public interface LocalPlanningDataProvider {

    public Category[] getEvents(Date startDate, Date endDate);

}
