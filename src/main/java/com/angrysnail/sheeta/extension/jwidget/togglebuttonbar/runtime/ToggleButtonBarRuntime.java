package com.angrysnail.sheeta.extension.jwidget.togglebuttonbar.runtime;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.runtime.ButtonGroupRuntime;
import com.angrysnail.sheeta.extension.jwidget.togglebuttonbar.edition.ToggleButtonBarEdition;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class handling a button jwidget for the runtime
 * 
 * @author ITRIS, Jordi SUC
 */
public class ToggleButtonBarRuntime extends ButtonGroupRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "ToggleButtonBarWidget";

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public ToggleButtonBarRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ToggleButtonBarEdition.class;
	}
}
