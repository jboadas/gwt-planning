package org.gwtplanning.client.widgets;

import com.gwtext.client.widgets.form.TimeField;

/**
 * DOC stephane class global comment. Detailled comment <br/>
 */
public class SimpleTimeField extends TimeField {

    public SimpleTimeField() {
        super();
        setIncrement(60);
        setFormat("H:i");
    }
}
