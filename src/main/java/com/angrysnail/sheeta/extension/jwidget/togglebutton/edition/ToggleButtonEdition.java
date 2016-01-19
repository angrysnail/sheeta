package com.angrysnail.sheeta.extension.jwidget.togglebutton.edition;

import java.awt.Frame;

import com.angrysnail.sheeta.extension.jwidget.base.edition.AbstractButtonEdition;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class ToggleButtonEdition extends AbstractButtonEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public ToggleButtonEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, TOGGLE_BUTTON, "ToggleButtonWidget", 2);
	}
}
