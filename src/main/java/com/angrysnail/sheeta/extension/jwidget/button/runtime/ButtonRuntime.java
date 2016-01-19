package com.angrysnail.sheeta.extension.jwidget.button.runtime;

import javax.swing.JButton;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.runtime.AbstractButtonRuntime;
import com.angrysnail.sheeta.extension.jwidget.button.edition.ButtonEdition;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class handling a button jwidget for the runtime
 * 
 * @author ITRIS, Jordi SUC
 */
public class ButtonRuntime extends AbstractButtonRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "ButtonWidget";

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public ButtonRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		component = new JButton();
		super.initialize();
		handleLook();
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ButtonEdition.class;
	}
}
