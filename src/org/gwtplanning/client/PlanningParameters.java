package org.gwtplanning.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gwtplanning.client.i18n.PlanningMessages;
import org.gwtplanning.client.model.ScaleMode;
import org.gwtplanning.client.widgets.SimpleTimeField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.util.DateUtil;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.TimeField;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.layout.HorizontalLayout;

/**
 * DOC stephane class global comment. Detailled comment
 */
public class PlanningParameters extends SimplePanel {

    private static PlanningMessages messages = (PlanningMessages) GWT.create(PlanningMessages.class);

    private TimeField startTimeField;

    private TimeField endTimeField;

    private DateField startDateField;

    private DateField endDateField;

    public PlanningParameters() {
        Date tmp = new Date();
        Date startDateDefaultValue = new Date();// DateUtil.add(tmp, DateUtil.DAY, -1);
        Date endDateDefaultValue = DateUtil.add(tmp, DateUtil.DAY, 1);

        Panel formPanel = new Panel();
        formPanel.setBaseCls("planning-params");
        formPanel.setWidth(900);
        formPanel.setBorder(true);
        formPanel.setLayout(new HorizontalLayout(5));

        formPanel.add(new Label(messages.parameters_startDate() + ":", false));
        startDateField = new DateField(messages.parameters_startDate(), "startDate", 80);
        startDateField.setValue(startDateDefaultValue);
        formPanel.add(startDateField);
        startTimeField = new SimpleTimeField();
        startTimeField.setHideLabel(true);
        startTimeField.setWidth(90);
        formPanel.add(startTimeField);

        formPanel.add(new Label(messages.parameters_endDate() + ":", false));
        endDateField = new DateField(messages.parameters_endDate(), "endDate", 80);
        endDateField.setValue(endDateDefaultValue);
        formPanel.add(endDateField);
        endTimeField = new SimpleTimeField();
        endTimeField.setHideLabel(true);
        endTimeField.setWidth(90);
        formPanel.add(endTimeField);

        setDefaultValues();
        // addListeners();

        Button validationButton = new Button(messages.parameters_validButton());
        validationButton.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                fireChange();
            }

        });

        formPanel.add(validationButton);

        this.add(formPanel);
    }

    private void setDefaultValues() {
        startTimeField.setValue("0:00");
        endTimeField.setValue("0:00");
    }

    private void addListeners() {
        TextFieldListenerAdapter listener = new TextFieldListenerAdapter() {

            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                fireChange();
            }

        };
        startDateField.addListener(listener);
        startTimeField.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                fireChange();
            }

        });
        endDateField.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                fireChange();
            }

        });
        endTimeField.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onChange(Field field, Object newVal, Object oldVal) {
                fireChange();
            }

        });
    }

    public ScaleMode getScaleMode() {
        long diff = getEndDate().getTime() - getStartDate().getTime();
        return ScaleMode.get(diff);
    }

    public Date getStartDate() {
        return convertDateFields(startDateField.getValue(), startTimeField.getValueAsString());
    }

    public Date getEndDate() {
        return convertDateFields(endDateField.getValue(), endTimeField.getValueAsString());

    }

    private Date convertDateFields(Date date, String time) {
        String fullDateTime = DateTimeFormat.getFormat("yyyy.MM.dd").format(date) + " " + time;
        return DateTimeFormat.getFormat("yyyy.MM.dd H:mm").parse(fullDateTime);
    }

    /**
     * Listener used to fire when a parameter value change.
     * 
     * In this version, the fire is called when the 'refresh' button is clicked.
     */
    public interface ParamChangeListener {

        public void onChange();
    }

    List<ParamChangeListener> listeners = new ArrayList<ParamChangeListener>();

    public void addChangeListener(ParamChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ParamChangeListener listener) {
        listeners.remove(listener);
    }

    private void fireChange() {
        try {
            for (ParamChangeListener listener : listeners) {
                listener.onChange();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
