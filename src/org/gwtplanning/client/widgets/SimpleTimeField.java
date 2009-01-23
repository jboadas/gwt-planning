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
