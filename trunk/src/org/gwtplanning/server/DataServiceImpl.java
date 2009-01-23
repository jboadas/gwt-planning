package org.gwtplanning.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.gwtplanning.client.model.Category;
import org.gwtplanning.client.model.CategoryImpl;
import org.gwtplanning.client.model.Event;
import org.gwtplanning.client.model.EventImpl;
import org.gwtplanning.client.test.DataService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * DOC stephane class global comment. Detailled comment <br/>
 */
public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    Random random = new Random();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.test.client.test.DataService#getData()
     */
    public Category[] getData(Date startDate, Date endDate) {
        ArrayList<Category> toReturn = new ArrayList<Category>();

        for (int i = 1; i <= 12; i++) {
            CategoryImpl category = new CategoryImpl("Task " + i);
            toReturn.add(category);

            List<Event> events = new ArrayList<Event>();

            Date currentEndDate = startDate;
            while (currentEndDate.before(endDate)) {
                EventImpl event = new EventImpl();

                event.setStart(new Date(currentEndDate.getTime() + random.nextInt(12 * 60 * 60 * 1000)));
                event.setEnd(new Date(event.getStart().getTime() + random.nextInt(180 * 60 * 1000)));

                currentEndDate = event.getEnd();

                int nextInt = random.nextInt(3);
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
