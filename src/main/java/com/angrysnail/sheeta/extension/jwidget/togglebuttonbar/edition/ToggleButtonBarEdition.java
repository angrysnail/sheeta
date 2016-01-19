package com.angrysnail.sheeta.extension.jwidget.togglebuttonbar.edition;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JToggleButton;
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
public class ToggleButtonBarEdition extends ButtonGroupEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public ToggleButtonBarEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, TOGGLE_BUTTON_BAR,
				"ToggleButtonBarWidget", 12);
	}

	@Override
	protected BufferedImage createImage(Element jwidgetElement, Dimension size) {

		// creating the image
		BufferedImage image = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		Element childJWidgetElement = null;
		String label = "";
		JToggleButton button = null;

		// creating all the child nodes components
		LinkedList<Component> components = new LinkedList<Component>();

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

				button = new JToggleButton(label);
				button.setOpaque(true);
				components.add(button);
			}
		}

		// handling the look of the buttons
		JWidgetToolkit.handleLook(jwidgetElement, new HashSet<Component>(
				components));

		// getting whether the component should be opaque
		boolean isOpaque = true;

		try {
			isOpaque = Boolean.parseBoolean(jwidgetElement
					.getAttribute(JWidgetToolkit.isOpaqueName));
		} catch (Exception ex) {
		}

		if (!isOpaque) {

			toolBar.setOpaque(false);
			toolBar.setBorder(new EmptyBorder(0, 0, 0, 0));
		}

		// setting the orientation of the tool bar
		String orientationStr = jwidgetElement.getAttribute("orientation");
		int orientation = orientationStr.equals("vertical")
				? SwingConstants.VERTICAL
				: SwingConstants.HORIZONTAL;
		toolBar.setOrientation(orientation);

		// checking whether all the buttons should have the same size
		/*
		 * boolean sameSizeForButtons=false;
		 * 
		 * try{ sameSizeForButtons=Boolean.parseBoolean(
		 * jwidgetElement.getAttribute(JWidgetToolkit.sameSizeForButtonsName));
		 * }catch (Exception ex){}
		 * 
		 * if(sameSizeForButtons){
		 * 
		 * //setting the new layout manager for the tool bar
		 * switch(orientation){
		 * 
		 * case SwingConstants.HORIZONTAL :
		 * 
		 * toolBar.setLayout(new GridLayout(components.size(), 1)); break;
		 * 
		 * case SwingConstants.VERTICAL :
		 * 
		 * toolBar.setLayout(new GridLayout(1, components.size())); break; }
		 * 
		 * toolBar.validate(); }
		 */

		// adding all the buttons to the toolbar
		for (Component cmp : components) {

			toolBar.add(cmp);
		}

		// setting the size of the toolbar
		toolBar.setBounds(0, 0, size.width, size.height);
		toolBar.setPreferredSize(size);
		toolBar.doLayout();
		toolBar.print(image.getGraphics());

		return image;
	}
}
