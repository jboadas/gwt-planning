package org.gwtplanning.client.model.data;

import java.util.Date;

import org.gwtplanning.client.model.Category;
import org.gwtplanning.client.test.DataService;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Random implementation of PlanningDataProvider. Generates random data.
 */
public class RemotePlanningDataProvider implements AsyncPlanningDataProvider {

    public void getEvents(Date startDate, Date endDate, AsyncCallback<Category[]> callback) {
        DataService.Util.getInstance().getData(startDate, endDate, callback);
    }

}
