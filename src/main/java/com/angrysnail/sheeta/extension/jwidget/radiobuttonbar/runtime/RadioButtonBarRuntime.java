package com.angrysnail.sheeta.extension.jwidget.radiobuttonbar.runtime;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.runtime.ButtonGroupRuntime;
import com.angrysnail.sheeta.extension.jwidget.radiobuttonbar.edition.RadioButtonBarEdition;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class handling a button jwidget for the runtime
 * 
 * @author ITRIS, Jordi SUC
 */
public class RadioButtonBarRuntime extends ButtonGroupRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "RadioButtonBarWidget";

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public RadioButtonBarRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return RadioButtonBarEdition.class;
	}
}
