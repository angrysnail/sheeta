package com.angrysnail.sheeta.extension.jwidget.radiobuttonbar.edition;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angrysnail.sheeta.extension.jwidget.base.edition.ButtonGroupEdition;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class RadioButtonBarEdition extends ButtonGroupEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public RadioButtonBarEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, RADIO_BUTTON_BAR,
				"RadioButtonBarWidget", 13);
	}

	@Override
	protected BufferedImage createImage(Element jwidgetElement, Dimension size) {

		// creating the image
		BufferedImage image = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		JToolBar toolBar = new JToolBar();

		// setting the properties of the panel
		toolBar.setOpaque(false);
		toolBar.setFloatable(false);
		toolBar.setBorder(new EmptyBorder(0, 0, 0, 0));

		// getting the orientation of the panel
		String orientationStr = jwidgetElement.getAttribute("orientation");
		int orientation = orientationStr.equals("vertical")
				? SwingConstants.VERTICAL
				: SwingConstants.HORIZONTAL;
		toolBar.setLayout(new BoxLayout(toolBar,
				(orientation == SwingConstants.VERTICAL
						? BoxLayout.Y_AXIS
						: BoxLayout.X_AXIS)));

		// creating all the child nodes components
		Element childJWidgetElement = null;
		String label = "";
		JRadioButton button = null;

		Set<Component> components = new HashSet<Component>();

		for (Node childJWidgetNode = jwidgetElement.getFirstChild(); childJWidgetNode != null; childJWidgetNode = childJWidgetNode
				.getNextSibling()) {

			if (childJWidgetNode instanceof Element
					&& childJWidgetNode
							.getNodeName()
							.equals(com.angrysnail.sheeta.library.Toolkit.jwidgetElementName)) {

				childJWidgetElement = (Element) childJWidgetNode;
				label = childJWidgetElement
						.getAttribute(com.angrysnail.sheeta.library.Toolkit.labelAttribute);
				label = (label == null) ? " " : label;

				button = new JRadioButton(label);
				button.setOpaque(false);
				toolBar.add(button);
				components.add(button);
			}
		}

		// handling the look for the buttons
		JWidgetToolkit.handleLook(jwidgetElement, components);

		// setting the size of the toolbar
		toolBar.setBounds(0, 0, size.width, size.height);
		toolBar.setPreferredSize(size);
		toolBar.doLayout();
		toolBar.print(image.getGraphics());

		return image;
	}
}
