package com.angrysnail.sheeta.extension.jwidget.popup.edition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.edition.MenuItemsContainerEdition;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class PopUpEdition extends MenuItemsContainerEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public PopUpEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, POP_UP, "PopUpMenuWidget", 11);
	}

	@Override
	protected BufferedImage createImage(Element jwidgetElement, Dimension size) {

		// creating the image
		BufferedImage image = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);

		String zoneLabel = "";

		try {
			zoneLabel = bundle.getString("zoneLabel");
		} catch (Exception ex) {
		}

		JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText(zoneLabel);
		label.setForeground(Color.lightGray);
		label.setBorder(new LineBorder(Color.lightGray));
		label.setSize(size);

		label.print(image.getGraphics());

		return image;
	}
}
