/*
 * Created on 18 juil. 2004
 * 
 =============================================
                   GNU LESSER GENERAL PUBLIC LICENSE Version 2.1
 =============================================
GLIPS Graffiti Editor, a SVG Editor
Copyright (C) 2003 Jordi SUC, Philippe Gil, SARL ITRIS

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Contact : jordi.suc@itris.fr; philippe.gil@itris.fr

 =============================================
 */
package com.angrysnail.sheeta.svgeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.svgeditor.actions.popup.PopupItem;
import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the class used to display the "about" dialog
 * 
 * @author ITRIS, Jordi SUC
 *
 */
public class AboutModule extends ModuleAdapter {

	/**
	 * the id
	 */
	private String idAbout = "About";

	/**
	 * the labels
	 */
	private String labelAbout = "";

	/**
	 * the menu item
	 */
	private final JMenuItem about = new JMenuItem();

	/**
	 * the resource bundle
	 */
	private ResourceBundle bundle;

	/**
	 * the editor
	 */
	private Editor editor;

	/**
	 * the listener to the about menu items
	 */
	private ActionListener aboutListener;

	/**
	 * the constructor of the class
	 * 
	 * @param editor
	 *            the editor
	 */
	public AboutModule(Editor editor) {

		this.editor = editor;

		// the resource bundle
		bundle = ResourcesManager.bundle;

		if (bundle != null) {

			try {
				labelAbout = bundle.getString("labelabout");
			} catch (Exception ex) {
			}
		}

		// getting the icons
		ImageIcon aboutIcon = ResourcesManager.getIcon("About", false), daboutIcon = ResourcesManager
				.getIcon("About", true);

		// the menuitem
		about.setText(labelAbout);
		about.setIcon(aboutIcon);
		about.setDisabledIcon(daboutIcon);

		// the dialog
		final AboutDialog aboutDialog = new AboutDialog();

		// creating the listener to the menu item
		aboutListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				aboutDialog.setLocationRelativeTo(Editor.getParent());
				aboutDialog.setVisible(true);
			}
		};

		about.addActionListener(aboutListener);
	}

	@Override
	public HashMap<String, JMenuItem> getMenuItems() {

		HashMap<String, JMenuItem> menuItems = new HashMap<String, JMenuItem>();
		menuItems.put(idAbout, about);

		return menuItems;
	}

	@Override
	public Collection<PopupItem> getPopupItems() {

		LinkedList<PopupItem> popupItems = new LinkedList<PopupItem>();

		// creating the about popup item
		PopupItem item = new PopupItem(getSVGEditor(), idAbout, labelAbout,
				"About") {

			@Override
			public JMenuItem getPopupItem(LinkedList<Element> nodes) {

				// adds the action listeners
				menuItem.addActionListener(aboutListener);

				return super.getPopupItem(nodes);
			}
		};

		popupItems.add(item);

		return popupItems;
	}

	/**
	 * @return the editor
	 */
	public Editor getSVGEditor() {
		return editor;
	}

	/**
	 * the dialog that will be shown
	 * 
	 * @author ITRIS, Jordi SUC
	 */
	protected class AboutDialog extends JDialog {

		/**
		 * the dialog frame
		 */
		private final JDialog dialog = this;

		/**
		 * the constructor of the class
		 */
		protected AboutDialog() {

			setTitle(labelAbout);

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());

			final ImageIcon image = ResourcesManager.getIcon("Splash", false);

			if (image != null) {

				// creating the panel that will contain the image
				JPanel imagePanel = new JPanel() {

					@Override
					protected void paintComponent(Graphics evt) {

						super.paintComponent(evt);
						image.paintIcon(this, evt, 0, 0);
					}
				};

				imagePanel.setLayout(null);
				imagePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

				imagePanel.setPreferredSize(new Dimension(image.getIconWidth(),
						image.getIconHeight()));
				panel.add(imagePanel, BorderLayout.CENTER);
			}

			// creating the panel that will contain the button
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.add(buttonPanel, BorderLayout.SOUTH);

			// creating the ok button
			String okLabel = "";

			// gets the labels from the resources

			if (bundle != null) {

				try {
					okLabel = bundle.getString("labelok");
				} catch (Exception ex) {
				}
			}

			final JButton okButton = new JButton(okLabel);
			buttonPanel.add(okButton);

			// adding the listeners
			final ActionListener actionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					dialog.setVisible(false);
				}
			};

			okButton.addActionListener(actionListener);

			// the ok action name
			String actionName = "okAction";

			// registering the ok action
			Action okAction = new AbstractAction(actionName) {

				@Override
				public void actionPerformed(ActionEvent e) {

					actionListener.actionPerformed(e);
				}
			};

			okButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), actionName);
			okButton.getActionMap().put(actionName, okAction);

			getContentPane().add(panel);
			pack();
		}
	}
}
