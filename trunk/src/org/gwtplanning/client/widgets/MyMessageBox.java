// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
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

import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;

/**
 * DOC stephane class global comment. Detailled comment
 */
public class MyMessageBox {

    private static MessageBoxConfig getConfig(String title, String message, String icon) {
        MessageBoxConfig config = new MessageBoxConfig();
        config.setMsg(message);
        config.setTitle(title);
        config.setIconCls(icon);
        config.setMinWidth(380);

        config.setButtons(MessageBox.OK);

        return config;
    }

    public static void warning(String title, String message) {
        MessageBox.show(getConfig(title, message, MessageBox.WARNING));
    }

    public static void info(String title, String message) {
        MessageBox.show(getConfig(title, message, MessageBox.INFO));
    }

    public static void error(String title, String message) {
        MessageBox.show(getConfig(title, message, MessageBox.ERROR));
    }

    public static void error(String title, String message, MessageBox.PromptCallback cb) {
        MessageBoxConfig config = getConfig(title, message, MessageBox.ERROR);
        config.setCallback(cb);
        MessageBox.show(config);
    }

    public static void prompt(String title, String message, boolean modal, MessageBox.PromptCallback cb) {
        MessageBoxConfig config = getConfig(title, message, MessageBox.QUESTION);
        config.setPrompt(true);
        config.setButtons(MessageBox.OKCANCEL);
        config.setCallback(cb);
        config.setModal(modal);
        MessageBox.show(config);
    }

    public static void prompt(String title, String message, MessageBox.PromptCallback cb) {
        prompt(title, message, true, cb);
    }
}
