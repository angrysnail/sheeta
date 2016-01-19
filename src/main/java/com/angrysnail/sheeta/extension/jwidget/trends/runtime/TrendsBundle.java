package com.angrysnail.sheeta.extension.jwidget.trends.runtime;

import java.util.ResourceBundle;

/**
 * the class of the bundle of the trends
 * 
 * @author ITRIS, Jordi SUC
 */
public class TrendsBundle {

	/**
	 * the resource bundle
	 */
	public static ResourceBundle bundle;

	static {

		// getting the resource bundle
		bundle = ResourceBundle
				.getBundle("com.angrysnail.sheeta.extension.jwidget.trends.runtime.properties.TrendsWidgetRuntime");
	}
}
