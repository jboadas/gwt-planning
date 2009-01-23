package org.gwtplanning.client.model.data;

import java.util.Date;

import org.gwtplanning.client.model.Category;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * DOC stephane class global comment. Detailled comment
 */
public interface AsyncPlanningDataProvider {

    public void getEvents(Date startDate, Date endDate, AsyncCallback<Category[]> callback);
}
