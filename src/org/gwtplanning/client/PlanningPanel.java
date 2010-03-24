package org.gwtplanning.client;

import java.util.Date;

import org.gwtplanning.client.i18n.PlanningMessages;
import org.gwtplanning.client.model.Category;
import org.gwtplanning.client.model.Event;
import org.gwtplanning.client.model.ScaleMode;
import org.gwtplanning.client.model.data.AsyncPlanningDataProvider;
import org.gwtplanning.client.model.data.LocalPlanningDataProvider;
import org.gwtplanning.client.model.data.PlanningDataProviderMode;
import org.gwtplanning.client.widgets.MyMessageBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

/**
 * DOC stephane class global comment. Detailled comment
 */
public class PlanningPanel extends SimplePanel {

    private static PlanningMessages messages = (PlanningMessages) GWT.create(PlanningMessages.class);

    private static final int EVENT_DIV_MIN_WIDTH = 22;

    private static final int EVENT_DIV_HEIGHT = 20;

    private static final int DEFAULT_CATEGORY_DIV_WIDTH = 100;

    private int currentCategoryDivWidth;

    private static final int ABSOLUTE_PANEL_WIDTH = 800;

    // Used to evaluate div width (w3c standard: visible width=width+border+padding):
    // Assumes that jobName panels and exec panels have the same value (for height, width)
    private static final int PANELS_BORDER_PADDING = 2;

    private static final int CATEGORY_DIV_BORDER_PADDING = 6;

    private static final int DATE_PANELS_BORDER_PADDING = 5;

    private static final int EVENT_FLOAT_PANEL_BORDER_PADDING = 4;

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
        if (endDate.before(startDate)) {
            MyMessageBox.error(messages.error(), messages.error_endBeforeStart());
            return;
        }

        currentCategoryDivWidth = evaluateCategoryDivWidth(data);

        int nbPeriods = evalNbPeriods(startDate, endDate, scaleMode);
        AbsolutePanel absolutePanel = new AbsolutePanel();
        absolutePanel.setStylePrimaryName("planning-wrapper");
        absolutePanel.setHeight(((data.length + 1) * EVENT_DIV_HEIGHT + 2) + "px");

        dataPanelsHeight = EVENT_DIV_HEIGHT * (data.length + 1) - PANELS_BORDER_PADDING;

        // Date panels:
        onePeriodColumnWidth = (ABSOLUTE_PANEL_WIDTH - currentCategoryDivWidth) / nbPeriods;
        for (int i = 0; i < nbPeriods; i++) {
            int xPosition = currentCategoryDivWidth + onePeriodColumnWidth * i;
            int yPosition = 0;

            // Date title:
            Date panelStartdate = new Date(startDate.getTime() + i * scaleMode.getPeriodInMillis());

            Widget dateTitle = getDateTitle(panelStartdate);
            absolutePanel.add(dateTitle, xPosition, yPosition);

            // Date panel:
            Widget datePanel = getDatePanel(i % 2 == 0);

            datePanel.setHeight(dataPanelsHeight + 1 + "px");

            if (i == nbPeriods - 1) {
                // Adjust width of last period to match global box:
                datePanel.setWidth(ABSOLUTE_PANEL_WIDTH - 1 - xPosition + "px");
            } else {
                datePanel.setWidth(onePeriodColumnWidth - DATE_PANELS_BORDER_PADDING + "px");
            }

            absolutePanel.add(datePanel, xPosition, yPosition);
        }

        final Date now = new Date();
        if (now.after(startDate) && now.before(endDate)) {
            // Add 'past' layer:
            long diff = now.getTime() - startDate.getTime();
            final int nowX = evaluateWidth(diff);
            Widget pastPanel = new SimplePanel();
            pastPanel.setPixelSize(nowX, dataPanelsHeight);
            pastPanel.setStylePrimaryName("planning-past");
            absolutePanel.add(pastPanel, currentCategoryDivWidth + 1, 2);

            // Add 'now' marker:
            Widget nowPanel = new SimplePanel();
            nowPanel.setPixelSize(1, dataPanelsHeight);
            nowPanel.setStylePrimaryName("planning-now");
            absolutePanel.add(nowPanel, currentCategoryDivWidth + nowX, 2);
        }

        // Job label panels:
        int j = 1;
        for (Category category : data) {
            Widget addJob = addCategory(category);

            addJob.setPixelSize(currentCategoryDivWidth - CATEGORY_DIV_BORDER_PADDING, EVENT_DIV_HEIGHT - PANELS_BORDER_PADDING
                    + 1);
            absolutePanel.add(addJob, 0, EVENT_DIV_HEIGHT * j);

            Widget jobHorizontalLines = new SimplePanel();
            jobHorizontalLines.setPixelSize(ABSOLUTE_PANEL_WIDTH, EVENT_DIV_HEIGHT - PANELS_BORDER_PADDING);
            jobHorizontalLines.setStylePrimaryName("planning-job-border");
            absolutePanel.add(jobHorizontalLines, 0, EVENT_DIV_HEIGHT * j);

            for (Event execBean : category.getEvents()) {
                if (execBean.getStart() != null && execBean.getEnd() != null) {
                    addEvent(absolutePanel, execBean, currentCategoryDivWidth, EVENT_DIV_HEIGHT * j);
                }
            }

            j++;
        }

        // Add global border:
        Widget borders = new SimplePanel();
        borders.setPixelSize(ABSOLUTE_PANEL_WIDTH - 2 - 2, dataPanelsHeight - EVENT_DIV_HEIGHT + 1);
        borders.setStylePrimaryName("planning-global-border");
        absolutePanel.add(borders, 0, EVENT_DIV_HEIGHT - 1);

        Widget borders2 = new SimplePanel();
        borders2.setPixelSize(ABSOLUTE_PANEL_WIDTH - 2 - 1 - currentCategoryDivWidth, dataPanelsHeight);
        borders2.setStylePrimaryName("planning-global-border");
        absolutePanel.add(borders2, currentCategoryDivWidth - 1, 0);

        // Layout:
        mainPanel = absolutePanel;

        scrollPanel = new ScrollPanel();
        scrollPanel.setPixelSize(ABSOLUTE_PANEL_WIDTH, 300);
        scrollPanel.add(mainPanel);

        this.add(scrollPanel);
    }

    private int evaluateCategoryDivWidth(Category[] data) {
        int max = DEFAULT_CATEGORY_DIV_WIDTH;
        for (Category category : data) {
            int offsetWidth = category.toString().length() * 9;
            if (offsetWidth > max)
                max = offsetWidth;
        }
        return max;
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

    private Widget getDateTitle(Date startDate) {
        String label = scaleMode.getDateFormat().format(startDate);
        final Label labelWidget = new Label(label);
        labelWidget.setStylePrimaryName("planning-date-title");
        labelWidget.setWidth(onePeriodColumnWidth + "px");
        labelWidget.setHeight(EVENT_DIV_HEIGHT - 4 + "px");
        labelWidget.setWordWrap(false);

        Date endDate = new Date(startDate.getTime() + scaleMode.getPeriodInMillis());

        final PopupPanel popuppanel = new PopupPanel();
        popuppanel.addStyleName("date-range-popup");
        com.google.gwt.user.client.ui.Panel popupContent = new VerticalPanel();
        popupContent.add(new HTML("<b>Period " + label + "</b><br/><br/>" + "From: <i>" + Constants.DATE_FORMAT.format(startDate)
                + "</i><br/>" + "To: <i>" + Constants.DATE_FORMAT.format(endDate) + "</i>"));
        popuppanel.setWidget(popupContent);

        labelWidget.addMouseListener(new MouseListenerAdapter() {

            @Override
            public void onMouseEnter(Widget sender) {
                popuppanel.setPopupPosition(labelWidget.getAbsoluteLeft(), labelWidget.getAbsoluteTop() + 24);
                popuppanel.show();
            }

            @Override
            public void onMouseLeave(Widget sender) {
                popuppanel.hide();
            }

        });

        return labelWidget;
    }

    private Widget getDatePanel(boolean odd) {
        String style = (odd ? "planning-timeline-odd" : "planning-timeline-even");
        com.google.gwt.user.client.ui.Panel date = new SimplePanel();
        date.setStylePrimaryName(style);
        return date;
    }

    private int evaluateWidth(long millis) {
        return new Long(millis * onePeriodColumnWidth / scaleMode.getPeriodInMillis()).intValue();
    }

    private void addEvent(final AbsolutePanel absolutePanel, final Event event, final int x, final int y) {
        long diff = event.getStart().getTime() - startDate.getTime();
        final int padding = evaluateWidth(diff);

        long ellapsed = event.getEnd().getTime() - event.getStart().getTime();
        int width = evaluateWidth(ellapsed);

        if (width < EVENT_DIV_MIN_WIDTH) {
            width = EVENT_DIV_MIN_WIDTH;
        }

        final SimplePanel execPanel = new EventPanel(event, width);

        Label w = new Label();
        execPanel.add(w);

        absolutePanel.add(execPanel, x + padding, y + 1);
    }

    /**
     * DOC smallet
     */
    private class EventPanel extends SimplePanel {

        Window window;

        Widget borders;

        final MyBoolean fixed = new MyBoolean();

        Event event;

        public EventPanel(Event event, int width) {
            super();
            this.event = event;

            getEventDecorator().decorate(event, this, false);

            this.sinkEvents(com.google.gwt.user.client.Event.ONMOUSEOVER + com.google.gwt.user.client.Event.ONMOUSEOUT
                    + com.google.gwt.user.client.Event.ONCLICK);
            this.setWidth(width - PANELS_BORDER_PADDING + "px");
            this.setHeight(EVENT_DIV_HEIGHT - PANELS_BORDER_PADDING - 1 + "px");
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
                            closeDetailsPopup(windowWrapper, EventPanel.this, event);
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

                borders.setWidth(this.getOffsetWidth() - EVENT_FLOAT_PANEL_BORDER_PADDING + "px");
                borders.setHeight(dataPanelsHeight - EVENT_DIV_HEIGHT + 1 + "px");
                borders.setVisible(false);
                int leftPosition = EventPanel.this.getAbsoluteLeft() - EventPanel.this.getParent().getAbsoluteLeft();
                ((AbsolutePanel) getParent()).add(borders, leftPosition, 0 + EVENT_DIV_HEIGHT + 1);
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

    private Widget addCategory(Category category) {
        com.google.gwt.user.client.ui.Panel categoryNamePanel = new SimplePanel();
        categoryNamePanel.setStylePrimaryName("planning-jobname");
        categoryNamePanel.add(new Label(category.toString()));

        return categoryNamePanel;
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
