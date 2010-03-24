// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.gwtplanning.client;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * DOC stephane class global comment. Detailled comment
 */
public class Constants {

    public static final String DATE_PATTERN = "dd-MMM-yyyy H:mm";

    public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(Constants.DATE_PATTERN);
}
