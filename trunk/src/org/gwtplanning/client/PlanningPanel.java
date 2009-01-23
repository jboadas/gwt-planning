package org.gwtplanning.client;

import java.util.Date;

import org.gwtplanning.client.model.Category;
import org.gwtplanning.client.model.Event;
import org.gwtplanning.client.model.ScaleMode;
import org.gwtplanning.client.model.data.AsyncPlanningDataProvider;
import org.gwtplanning.client.model.data.LocalPlanningDataProvider;
import org.gwtplanning.client.model.data.PlanningDataProviderMode;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

/**
 * DOC stephane class global comment. Detailled comment
 */
public class PlanningPanel extends SimplePanel {

    private static final int DIV_EXEC_MIN_WIDTH = 22;

    private static final int JOBS_PANEL_WIDTH = 150;

    private static final int EXEC_DIV_HEIGHT = 20;

    private static final int ABSOLUTE_PANEL_WIDTH = 800;

    // Used to evaluate div width (w3c standard: visible width=width+border+padding):
    // Assumes that jobName panels and exec panels have the same value (for height, width)
    private static final int PANELS_BORDER_PADDING = 2;

    private static final int JOB_PANELS_BORDER_PADDING = 6;

    private static final int DATE_PANELS_BORDER_PADDING = 5;

    private static final int EXEC_FLOAT_PANEL_BORDER_PADDING = 4;

    private static final String GLOBAL_PATTERN = "dd/MM/yyyy H:mm";

    private int dataPanelsHeight;

    private Date startDate;

    private Date endDate;

    private Object provider;

    private PlanningDataProviderMode dataProviderMode = PlanningDataProviderMode.LOCAL;

    private int onePeriodColumnWidth;

    private ScaleMode scaleMode = ScaleMode.DAY;

    private AbsolutePanel mainPanel;

    private ScrollPanel scrollPanel;

    private EventDetailsDecorator eventDetailsDecorator = new EventDetailsDecorator.EventDetailsDecoratorImpl();

    private EventDecorator eventDecorator = new EventDecorator.EventDecoratorImpl();

    public PlanningPanel(LocalPlanningDataProvider provider, Date startDate, Date endDate, ScaleMode scaleMode) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.scaleMode = scaleMode;
        this.dataProviderMode = PlanningDataProviderMode.LOCAL;
        this.provider = provider;

        this.setWidth(ABSOLUTE_PANEL_WIDTH + 1 + "px");
    }

    public PlanningPanel(AsyncPlanningDataProvider provider, Date startDate, Date endDate, ScaleMode scaleMode) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.scaleMode = scaleMode;
        this.dataProviderMode = PlanningDataProviderMode.REMOTE;
        this.provider = provider;

        this.setWidth(ABSOLUTE_PANEL_WIDTH + 1 + "px");
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setScaleMode(ScaleMode scaleMode) {
        this.scaleMode = scaleMode;
    }

    public EventDetailsDecorator getEventDetailsDecorator() {
        return this.eventDetailsDecorator;
    }

    public void setEventDetailsDecorator(EventDetailsDecorator eventDecorator) {
        this.eventDetailsDecorator = eventDecorator;
    }

    public EventDecorator getEventDecorator() {
        return this.eventDecorator;
    }

    public void setEventDecorator(EventDecorator eventDecorator) {
        this.eventDecorator = eventDecorator;
    }

    public void draw() {
        if (scrollPanel != null) {
            this.remove(scrollPanel);
        }

        switch (dataProviderMode) {
        case LOCAL:
            Category[] data = ((LocalPlanningDataProvider) provider).getEvents(startDate, endDate);
            draw(data);
            break;
        case REMOTE:
            AsyncCallback<Category[]> asyncCallback = new AsyncCallback<Category[]>() {

                public void onFailure(Throwable caught) {

                }

                public void onSuccess(Category[] result) {
                    draw(result);
                }
            };
            ((AsyncPlanningDataProvider) provider).getEvents(startDate, endDate, asyncCallback);
            break;
        }
    }

    private void draw(Category[] data) {
        int nbPeriods = evalNbPeriods(startDate, endDate, scaleMode);
        AbsolutePanel absolutePanel = new AbsolutePanel();
        absolutePanel.setStylePrimaryName("planning-wrapper");
        absolutePanel.setHeight(((data.length + 1) * EXEC_DIV_HEIGHT + 2) + "px");

        dataPanelsHeight = EXEC_DIV_HEIGHT * (data.length + 1) - PANELS_BORDER_PADDING;

        // Date panels:
        onePeriodColumnWidth = (ABSOLUTE_PANEL_WIDTH - JOBS_PANEL_WIDTH) / nbPeriods;
        for (int i = 0; i < nbPeriods; i++) {
            int xPosition = JOBS_PANEL_WIDTH + onePeriodColumnWidth * i;
            int yPosition = 0;

            String style = (i % 2 == 0 ? "planning-timeline-odd" : "planning-timeline-even");

            Date panelStartdate = new Date(startDate.getTime() + i * scaleMode.getPeriodInMillis());

            Widget datePanel = addDatePanel(absolutePanel, style, xPosition, yPosition, panelStartdate);

            datePanel.setHeight(dataPanelsHeight + 1 + "px");

            if (i == nbPeriods - 1) {
                // Adjust width of last period to match global box:
                datePanel.setWidth(ABSOLUTE_PANEL_WIDTH - 1 - xPosition + "px");
            } else {
                datePanel.setWidth(onePeriodColumnWidth - DATE_PANELS_BORDER_PADDING + "px");
            }

            absolutePanel.add(datePanel, xPosition, yPosition);
        }

        // Add 'past' layer:
        long diff = new Date().getTime() - startDate.getTime();
        final int padding = evaluateWidth(diff);
        Widget past = new SimplePanel();
        past.setPixelSize(padding, dataPanelsHeight);
        past.setStylePrimaryName("planning-past");
        absolutePanel.add(past, JOBS_PANEL_WIDTH + 1, 2);

        // Add 'now' marker:
        Widget now = new SimplePanel();
        now.setPixelSize(1, dataPanelsHeight);
        now.setStylePrimaryName("planning-now");
        absolutePanel.add(now, JOBS_PANEL_WIDTH + padding, 2);

        // Job label panels:
        int j = 1;
        for (Category category : data) {
            Widget addJob = addJob(category);

            addJob.setPixelSize(JOBS_PANEL_WIDTH - JOB_PANELS_BORDER_PADDING, EXEC_DIV_HEIGHT - PANELS_BORDER_PADDING + 1);
            absolutePanel.add(addJob, 0, EXEC_DIV_HEIGHT * j);

            Widget jobHorizontalLines = new SimplePanel();
            jobHorizontalLines.setPixelSize(ABSOLUTE_PANEL_WIDTH, EXEC_DIV_HEIGHT - PANELS_BORDER_PADDING);
            jobHorizontalLines.setStylePrimaryName("planning-job-border");
            absolutePanel.add(jobHorizontalLines, 0, EXEC_DIV_HEIGHT * j);

            for (Event execBean : category.getEvents()) {
                if (execBean.getStart() != null && execBean.getEnd() != null) {
                    addJobExec(absolutePanel, execBean, JOBS_PANEL_WIDTH, EXEC_DIV_HEIGHT * j);
                }
            }

            j++;
        }

        // Add global border:
        Widget borders = new SimplePanel();
        borders.setPixelSize(ABSOLUTE_PANEL_WIDTH - 2 - 2, dataPanelsHeight - EXEC_DIV_HEIGHT + 1);
        borders.setStylePrimaryName("planning-global-border");
        absolutePanel.add(borders, 0, EXEC_DIV_HEIGHT - 1);

        Widget borders2 = new SimplePanel();
        borders2.setPixelSize(ABSOLUTE_PANEL_WIDTH - 2 - 1 - JOBS_PANEL_WIDTH, dataPanelsHeight);
        borders2.setStylePrimaryName("planning-global-border");
        absolutePanel.add(borders2, JOBS_PANEL_WIDTH - 1, 0);

        // Layout:
        mainPanel = absolutePanel;

        scrollPanel = new ScrollPanel();
        scrollPanel.setPixelSize(ABSOLUTE_PANEL_WIDTH, 300);
        scrollPanel.add(mainPanel);

        this.add(scrollPanel);
    }

    /**
     * DOC stephane Comment method "evalNbPeriods".
     * 
     * @param startDate
     * @param endDate
     * @param scaleMode
     * @return
     */
    private int evalNbPeriods(Date startDate, Date endDate, ScaleMode scaleMode) {
        int nbPeriods = new Long((endDate.getTime() - startDate.getTime()) / scaleMode.getPeriodInMillis()).intValue();
        return nbPeriods;
    }

    private Widget addDatePanel(AbsolutePanel absolutePanel, String style, final int xPosition, final int yPosition,
            Date startDate) {
        String label = scaleMode.getDateFormat().format(startDate);
        Date endDate = new Date(startDate.getTime() + scaleMode.getPeriodInMillis());

        com.google.gwt.user.client.ui.Panel date = new SimplePanel();

        Label labelWidget = new Label(label);
        labelWidget.setStylePrimaryName("planning-date-title");
        labelWidget.setWidth(onePeriodColumnWidth + "px");
        labelWidget.setHeight(EXEC_DIV_HEIGHT - 4 + "px");
        labelWidget.setWordWrap(false);
        absolutePanel.add(labelWidget, xPosition, yPosition);

        date.setStylePrimaryName(style);

        final Window window = new Window();
        window.setTitle("Period " + label);
        window.setWidth(180);
        window.setHeight(60);
        window.setPlain(true);
        window.setClosable(false);
        window.add(new Label("From " + DateTimeFormat.getFormat(GLOBAL_PATTERN).format(startDate)));
        window.add(new Label("To " + DateTimeFormat.getFormat(GLOBAL_PATTERN).format(endDate)));

        labelWidget.addMouseListener(new MouseListenerAdapter() {

            @Override
            public void onMouseEnter(Widget sender) {
                window.setPosition(mainPanel.getAbsoluteLeft() + xPosition, mainPanel.getAbsoluteTop() + yPosition + 24);
                window.show();
            }

            @Override
            public void onMouseLeave(Widget sender) {
                window.hide();
            }

        });

        return date;
    }

    private int evaluateWidth(long millis) {
        return new Long(millis * onePeriodColumnWidth / scaleMode.getPeriodInMillis()).intValue();
    }

    private void addJobExec(final AbsolutePanel absolutePanel, final Event event, final int x, final int y) {
        long diff = event.getStart().getTime() - startDate.getTime();
        final int padding = evaluateWidth(diff);

        long ellapsed = event.getEnd().getTime() - event.getStart().getTime();
        int width = evaluateWidth(ellapsed);

        if (width < DIV_EXEC_MIN_WIDTH) {
            width = DIV_EXEC_MIN_WIDTH;
        }

        final SimplePanel execPanel = new ExecPanel(event, width);

        Label w = new Label();
        execPanel.add(w);

        absolutePanel.add(execPanel, x + padding, y + 1);

    }

    /**
     * DOC smallet
     */
    private class ExecPanel extends SimplePanel {

        Window window;

        Widget borders;

        final MyBoolean fixed = new MyBoolean();

        Event event;

        public ExecPanel(Event event, int width) {
            super();
            this.event = event;

            getEventDecorator().decorate(event, this, false);

            this.sinkEvents(com.google.gwt.user.client.Event.ONMOUSEOVER + com.google.gwt.user.client.Event.ONMOUSEOUT
                    + com.google.gwt.user.client.Event.ONCLICK);
            this.setWidth(width - PANELS_BORDER_PADDING + "px");
            this.setHeight(EXEC_DIV_HEIGHT - PANELS_BORDER_PADDING - 1 + "px");
        }

        @Override
        public void onBrowserEvent(com.google.gwt.user.client.Event gevent) {
            if (gevent.getTypeInt() == com.google.gwt.user.client.Event.ONMOUSEOVER) {
                if (window == null) {
                    window = new Window();
                    getEventDetailsDecorator().decorate(event, window);

                    final Window windowWrapper = window;
                    windowWrapper.addListener(new WindowListenerAdapter() {

                        @Override
                        public boolean doBeforeClose(Panel panel) {
                            closeDetailsPopup(windowWrapper, ExecPanel.this, event);
                            fixed.value = false;
                            return false;
                        }

                    });
                }

                if (!window.isVisible()) {
                    window.setPosition(mainPanel.getAbsoluteLeft() + ABSOLUTE_PANEL_WIDTH + 10, scrollPanel.getAbsoluteTop());
                }
                window.show();
                showBorders();
                getEventDecorator().decorate(event, this, true);
            } else if (gevent.getTypeInt() == com.google.gwt.user.client.Event.ONMOUSEOUT) {
                hideBorders();
                if (!fixed.value) {
                    // closeDetailsPopup(windowWrapper[0], this, event);
                    window.hide();
                    getEventDecorator().decorate(event, this, false);
                }
            } else if (gevent.getTypeInt() == com.google.gwt.user.client.Event.ONCLICK) {
                window.show();
                fixed.value = true;
            }
        }

        private void showBorders() {
            if (borders == null) {
                borders = new SimplePanel();
                borders.setStylePrimaryName("planning-float");

                borders.setWidth(this.getOffsetWidth() - EXEC_FLOAT_PANEL_BORDER_PADDING + "px");
                borders.setHeight(dataPanelsHeight - EXEC_DIV_HEIGHT + 1 + "px");
                borders.setVisible(false);
                ((AbsolutePanel) getParent()).add(borders, this.getAbsoluteLeft(), 0 + EXEC_DIV_HEIGHT + 1);
            }

            borders.setVisible(true);
        }

        private void hideBorders() {
            borders.setVisible(false);
        }

        private void closeDetailsPopup(final Window window, SimplePanel panel, Event event) {
            window.hide();
            getEventDecorator().decorate(event, panel, false);
        }
    }

    private Widget addJob(Category jobBean) {
        com.google.gwt.user.client.ui.Panel jobNamePanel = new SimplePanel();
        jobNamePanel.setStylePrimaryName("planning-jobName");
        jobNamePanel.add(new Label(jobBean.toString()));

        return jobNamePanel;
    }

    /**
     * Inner class used to change value of a 'final' boolean.
     */
    private class MyBoolean {

        boolean value;

        public MyBoolean() {
            this(false);
        }

        public MyBoolean(boolean value) {
            super();
            this.value = value;
        }

    }
}
