package com.angrysnail.sheeta.rtda.action.tagevent;

import java.awt.Container;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtda.action.AbstractRunApplication;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;

/**
 * the class of the run application action
 * 
 * @author ITRIS, Jordi SUC
 */
public class TagEventRunApplication extends AbstractRunApplication {

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture this action is linked to
	 * @param projectName
	 *            the name of the project this action is linked to
	 * @param parent
	 *            the parent container
	 * @param component
	 *            the component on which the action is registered
	 * @param actionObject
	 *            the object to which the action applies, if it is not the
	 *            provided component
	 * @param actionElement
	 *            the dom element defining the action
	 * @param jwidgetRuntime
	 *            the jwidget runtime object, if this action is linked to a
	 *            jwidget
	 */
	public TagEventRunApplication(SVGPicture picture, String projectName,
			Container parent, JComponent component, Object actionObject,
			Element actionElement, JWidgetRuntime jwidgetRuntime) {

		super(picture, projectName, parent, component, actionObject,
				actionElement, jwidgetRuntime);

		eventsManager = new TagEventsManager(this);
		initializeAction();
	}
}
