package com.angrysnail.sheeta.extension.jwidget.togglebutton.runtime;

import javax.swing.JToggleButton;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.runtime.AbstractButtonRuntime;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.StateButtonSimpleSendCommand;
import com.angrysnail.sheeta.extension.jwidget.togglebutton.edition.ToggleButtonEdition;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class handling a button jwidget for the runtime
 * 
 * @author ITRIS, Jordi SUC
 */
public class ToggleButtonRuntime extends AbstractButtonRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "ToggleButtonWidget";

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public ToggleButtonRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		component = new JToggleButton();
		super.initialize();
		handleLook();
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ToggleButtonEdition.class;
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:simpleSendCommand")) {

				action = new StateButtonSimpleSendCommand(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else {

				action = super.createAction(actionElement);
			}
		}

		return action;
	}
}
