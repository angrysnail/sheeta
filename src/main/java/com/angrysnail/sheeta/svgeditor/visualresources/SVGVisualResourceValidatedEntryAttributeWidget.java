/*
 * Created on 26 août 2004
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
package com.angrysnail.sheeta.svgeditor.visualresources;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * the class of the widgets that will be displayed in the properties dialog, and
 * enabling to modify the properties (with a validated entry) of an attribute
 * (or a group of attributes) of a resource node.
 * 
 * @author ITRIS, Jordi SUC
 */
public class SVGVisualResourceValidatedEntryAttributeWidget
		extends
			SVGVisualResourceAttributeWidget {

	/**
	 * the constructor of the class
	 * 
	 * @param resourceObjectAttribute
	 *            the attribute object that will be modified
	 */
	public SVGVisualResourceValidatedEntryAttributeWidget(
			SVGVisualResourceObjectAttribute resourceObjectAttribute) {

		super(resourceObjectAttribute);

		buildComponent();
	}

	/**
	 * builds the component to be displayed
	 */
	protected void buildComponent() {

		// the text field in which the value will be entered
		final JTextField textField = new JTextField(
				resourceObjectAttribute.getValue(), 20);
		textField.moveCaretPosition(0);

		// adds a key listener to the textfield
		final KeyAdapter keyListener = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

					// modifies the value of the attribute
					resourceObjectAttribute.setValue(textField.getText());
				}
			}
		};

		textField.addKeyListener(keyListener);

		final JButton okButton = new JButton();
		Insets buttonInsets = new Insets(1, 1, 1, 1);
		okButton.setMargin(buttonInsets);

		if (bundle != null) {

			try {
				okButton.setText(bundle.getString("labelok"));
			} catch (Exception ex) {
			}
		}

		final ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// modifies the widgetValue of the property item
				resourceObjectAttribute.setValue(textField.getText());
			}
		};

		// adds a listener to the button
		okButton.addActionListener(listener);

		// creates the component that will be returned
		JPanel validatedPanel = new JPanel();
		validatedPanel.setLayout(new BorderLayout());
		validatedPanel.add(textField, BorderLayout.CENTER);
		validatedPanel.add(okButton, BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		validatedPanel.setBorder(new EmptyBorder(0, 30, 0, 0));

		component = panel;

		Runnable disposer = new Runnable() {

			@Override
			public void run() {

				// removes the listeners
				textField.removeKeyListener(keyListener);
				okButton.removeActionListener(listener);
			}
		};
	}

}
