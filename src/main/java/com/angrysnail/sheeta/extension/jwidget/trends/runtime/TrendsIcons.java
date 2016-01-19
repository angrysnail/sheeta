package com.angrysnail.sheeta.extension.jwidget.trends.runtime;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

/**
 * the class of the trends icons
 * 
 * @author ITRIS, Jordi SUC
 */
public class TrendsIcons {

	/**
	 * the resource bundle
	 */
	private static ResourceBundle bundle;

	static {

		try {
			bundle = ResourceBundle
					.getBundle("com.angrysnail.sheeta.extension.jwidget.trends.runtime.properties.icons");
		} catch (Exception ex) {
			bundle = null;
		}
	}

	/**
	 * the map of the icons
	 */
	private static Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	/**
	 * the map of the disabled icons
	 */
	private static Map<String, ImageIcon> disabledIcons = new HashMap<String, ImageIcon>();

	/**
	 * returns the icon corresponding to the given name
	 * 
	 * @param iconName
	 *            the name of an icon
	 * @return the icon corresponding to the given name
	 * @param isGrayIcon
	 *            whether the icon is a gray one
	 */
	public static ImageIcon getIcon(String iconName, boolean isGrayIcon) {

		ImageIcon icon = null;

		if (iconName != null && !iconName.equals("")) {

			if (icons.containsKey(iconName)) {

				icon = isGrayIcon ? disabledIcons.get(iconName) : icons
						.get(iconName);

			} else if (bundle != null) {

				String path = "";

				try {
					path = bundle.getString(iconName);
				} catch (Exception ex) {
				}

				if (path != null && !path.equals("")) {

					try {
						icon = new ImageIcon(new URL(TrendsIcons.class
								.getResource("icons/" + path).toExternalForm()));
					} catch (Exception ex) {
					}

					if (icon != null) {

						icons.put(iconName, icon);
						Image image = icon.getImage();
						ImageIcon grayIcon = new ImageIcon(
								GrayFilter.createDisabledImage(image));
						disabledIcons.put(iconName, grayIcon);

						if (isGrayIcon) {

							icon = grayIcon;
						}
					}
				}
			}
		}

		return icon;
	}
}
