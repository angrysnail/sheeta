package com.angrysnail.sheeta.library;

import java.util.ResourceBundle;

/**
 * the library's bundle
 * 
 * @author ITRIS, Jordi SUC
 */
public class Bundle {

	/**
	 * the bundle
	 */
	public static ResourceBundle bundle;

	static {

		try {
			bundle = ResourceBundle
					.getBundle("com.angrysnail.sheeta.library.properties.LibraryStrings");
		} catch (Exception ex) {
			bundle = null;
		}
	}
}
