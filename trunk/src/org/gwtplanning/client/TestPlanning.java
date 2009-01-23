package org.gwtplanning.client;

import org.gwtplanning.client.PlanningParameters.ParamChangeListener;
import org.gwtplanning.client.model.data.RandomPlanningDataProvider;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.widgets.Panel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestPlanning implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final PlanningParameters paramsPanel = new PlanningParameters();

        final PlanningPanel planningPanel = new PlanningPanel(new RandomPlanningDataProvider(5), paramsPanel.getStartDate(),
                paramsPanel.getEndDate(), paramsPanel.getScaleMode());

        planningPanel.draw();

        paramsPanel.addChangeListener(new ParamChangeListener() {

            public void onChange() {
                planningPanel.setStartDate(paramsPanel.getStartDate());
                planningPanel.setEndDate(paramsPanel.getEndDate());
                planningPanel.setScaleMode(paramsPanel.getScaleMode());
                planningPanel.draw();
            }
        });

        Panel toReturn = new Panel();

        toReturn.add(paramsPanel);
        toReturn.add(planningPanel);

        RootPanel.get().add(toReturn);
    }
}
