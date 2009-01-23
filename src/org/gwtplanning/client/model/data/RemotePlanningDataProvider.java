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
