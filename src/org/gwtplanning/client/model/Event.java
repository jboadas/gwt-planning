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
package org.gwtplanning.client.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Events to be shown in planning.
 */
public interface Event extends Serializable {

    public Date getStart();

    public Date getEnd();

    public String getStatus();

}
