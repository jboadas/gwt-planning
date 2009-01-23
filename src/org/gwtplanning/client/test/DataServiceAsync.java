package org.gwtplanning.client.test;

import java.util.Date;

import org.gwtplanning.client.model.Category;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * DOC stephane DataService class global comment. Detailled comment
 */

public interface DataServiceAsync {

    public void getData(Date startDate, Date endDate, AsyncCallback<Category[]> asyncCallback);
}
