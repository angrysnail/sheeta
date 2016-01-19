package com.angrysnail.sheeta.extension.jwidget.checkbox.edition;

import java.awt.Frame;

import com.angrysnail.sheeta.extension.jwidget.base.edition.AbstractButtonEdition;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class CheckBoxEdition extends AbstractButtonEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public CheckBoxEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, CHECK_BOX, "CheckBoxWidget", 1);
	}
}
