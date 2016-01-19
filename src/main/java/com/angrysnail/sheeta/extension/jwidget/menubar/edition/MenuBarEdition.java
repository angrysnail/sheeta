package com.angrysnail.sheeta.extension.jwidget.menubar.edition;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angrysnail.sheeta.extension.jwidget.base.edition.MenuItemsContainerEdition;
import com.angrysnail.sheeta.library.Toolkit;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetManager;

/**
 * the class of the widget of a button
 * 
 * @author ITRIS, Jordi SUC
 */
public class MenuBarEdition extends MenuItemsContainerEdition {

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetManager
	 *            the jwidget manager
	 * @param mainFrame
	 *            the main frame
	 */
	public MenuBarEdition(JWidgetManager jwidgetManager, Frame mainFrame) {

		super(jwidgetManager, mainFrame, MENU_BAR, "MenuBarWidget", 10);
	}

	@Override
	protected BufferedImage createImage(Element jwidgetElement, Dimension size) {

		// creating the image
		BufferedImage image = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		JMenuBar menuBar = new JMenuBar();
		JWidgetToolkit.handleLook(jwidgetElement, menuBar);

		// filling the menu bar with the menu items defined
		// by the children that are directly under the jwidget element
		Element childJWidgetElement = null;
		String label = "";
		JMenu menu = null;
		Set<Component> menusSet = new HashSet<Component>();

		for (Node childJWidgetNode = jwidgetElement.getFirstChild(); childJWidgetNode != null; childJWidgetNode = childJWidgetNode
				.getNextSibling()) {

			if (childJWidgetNode instanceof Element
					&& childJWidgetNode.getNodeName().equals(
							Toolkit.jwidgetElementName)) {

				childJWidgetElement = (Element) childJWidgetNode;
				label = childJWidgetElement
						.getAttribute(Toolkit.labelAttribute);
				label = (label == null) ? " " : label;

				menu = new JMenu(label);
				menusSet.add(menu);
				menuBar.add(menu);
			}
		}

		// handling the look of each menu item
		JWidgetToolkit.handleLook(jwidgetElement, menusSet);

		JFrame frame = new JFrame();
		frame.setSize(size);
		menuBar.setSize(size);
		menuBar.setPreferredSize(size);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setJMenuBar(menuBar);
		frame.pack();
		menuBar.print(image.getGraphics());

		return image;
	}
}
