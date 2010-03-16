package org.gwtplanning.client.model;

import org.gwtplanning.client.i18n.PlanningMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Represents differents type of scale usable in the planning.
 */
public enum ScaleMode {
    DAY(24 * 60 * 60 * 1000, "yyyy-MM-dd", ((PlanningMessages) GWT.create(PlanningMessages.class)).DAY()),
    HALF_DAY(12 * 60 * 60 * 1000, "yyyy-MM-dd H:mm", ((PlanningMessages) GWT.create(PlanningMessages.class)).HALF_DAY()),
    FOUR_HOUR(4 * 60 * 60 * 1000, "H:mm", ((PlanningMessages) GWT.create(PlanningMessages.class)).FOUR_HOUR()),
    HOUR(1 * 60 * 60 * 1000, "H:mm", ((PlanningMessages) GWT.create(PlanningMessages.class)).HOUR()),
    TWENTY(20 * 60 * 1000, "H:mm", ((PlanningMessages) GWT.create(PlanningMessages.class)).TWENTY()),
    FIVE(5 * 60 * 1000, "H:mm", ((PlanningMessages) GWT.create(PlanningMessages.class)).FIVE());

    private long periodInMillis;

    private String pattern;

    private String label;

    private ScaleMode(long periodInMillis, String pattern, String label) {
        this.periodInMillis = periodInMillis;
        this.pattern = pattern;
        this.label = label;
    }

    public DateTimeFormat getDateFormat() {
        return DateTimeFormat.getFormat(pattern);
    }

    public long getPeriodInMillis() {
        return this.periodInMillis;
    }

    public String getPattern() {
        return this.pattern;
    }

    public String getLabel() {
        return this.label;
    }

    public static ScaleMode get(String key) {
        for (ScaleMode current : values()) {
            if (current.toString().equals(key)) {
                return current;
            }
        }

        return null;
    }
}
