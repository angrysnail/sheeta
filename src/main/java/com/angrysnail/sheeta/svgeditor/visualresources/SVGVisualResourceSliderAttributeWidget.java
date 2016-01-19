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
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.angrysnail.sheeta.svgeditor.Editor;

/**
 * the class of the widgets that will be displayed in the properties dialog, and
 * enabling to modify the properties (with a slider) of an attribute (or a group
 * of attributes) of a resource node.
 * 
 * @author ITRIS, Jordi SUC
 */
public class SVGVisualResourceSliderAttributeWidget
		extends
			SVGVisualResourceAttributeWidget {

	/**
	 * the constructor of the class
	 * 
	 * @param resourceObjectAttribute
	 *            the attribute object that will be modified
	 */
	public SVGVisualResourceSliderAttributeWidget(
			SVGVisualResourceObjectAttribute resourceObjectAttribute) {

		super(resourceObjectAttribute);

		buildComponent();
	}

	/**
	 * builds the component to be displayed
	 */
	protected void buildComponent() {

		if (resourceObjectAttribute != null) {

			final Editor svgEditor = resourceObjectAttribute.getModel()
					.getVisualResources().getSVGEditor();

			// the panel that will contain the widgets
			final JPanel displayAndSlider = new JPanel();

			// the initial value
			int val = (int) svgEditor.getSVGToolkit().getDoubleValue(
					resourceObjectAttribute.getValue(), true);

			final JSlider slider = new JSlider(0, 100, val);
			slider.setPreferredSize(new Dimension(100, 20));

			final JLabel displayedValue = new JLabel(val + " %");
			displayedValue.setPreferredSize(new Dimension(30, 20));

			final MouseAdapter sliderListener = new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent evt) {

					// modifies the value of the attribute
					resourceObjectAttribute.setValue(format.format(slider
							.getValue()) + "%");
				}
			};

			// adds a listener to the slider
			slider.addMouseListener(sliderListener);

			final ChangeListener sliderChangeListener = new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {

					displayedValue.setText(slider.getValue() + " %");
				}
			};

			// adds a listener to the slider
			slider.addChangeListener(sliderChangeListener);

			// creates the panel that will be displayed
			displayAndSlider.setLayout(new BorderLayout());
			displayAndSlider.add(slider, BorderLayout.CENTER);
			displayAndSlider.add(displayedValue, BorderLayout.EAST);
			displayAndSlider.setBorder(new EmptyBorder(0, 20, 0, 0));

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			panel.add(displayAndSlider);

			component = panel;

			disposer = new Runnable() {

				@Override
				public void run() {

					// removes the listeners
					slider.removeMouseListener(sliderListener);
					slider.removeChangeListener(sliderChangeListener);
				}
			};
		}
	}
}
