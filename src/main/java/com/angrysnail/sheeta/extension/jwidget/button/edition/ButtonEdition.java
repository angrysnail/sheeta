package com.angrysnail.sheeta.extension.jwidget.button.edition;

import java.awt.Frame;

import com.angrysnail.sheeta.extension.jwidget.base.edition.AbstractButtonEdition;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class ButtonEdition extends AbstractButtonEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public ButtonEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, BUTTON, "ButtonWidget", 0);
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ButtonEdition.class;
	}
}
