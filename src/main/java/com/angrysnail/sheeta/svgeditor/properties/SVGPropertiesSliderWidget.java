/*
 * Created on 19 janv. 2005
 * 
 =============================================
                   GNU LESSER GENERAL PUBLIC LICENSE Version 2.1
 =============================================
GLIPS Graffiti Editor, a SVG Editor
Copyright (C) 2004 Jordi SUC, Philippe Gil, SARL ITRIS

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
package com.angrysnail.sheeta.svgeditor.properties;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author ITRIS, Jordi SUC
 */
public class SVGPropertiesSliderWidget extends SVGPropertiesWidget {

	/**
	 * the constructor of the class
	 * 
	 * @param propertyItem
	 *            a property item
	 */
	public SVGPropertiesSliderWidget(SVGPropertyItem propertyItem) {

		super(propertyItem);

		buildComponent();
	}

	/**
	 * builds the component that will be displayed
	 */
	protected void buildComponent() {

		// the panel that will contain the widgets
		final JPanel displayAndSlider = new JPanel();

		// the initial value
		String value = propertyItem.getGeneralPropertyValue();
		int val = 100;

		try {
			val = (int) (Double.parseDouble(value) * 100);
		} catch (Exception ex) {
			val = 100;
		}

		final JSlider slider = new JSlider(0, 100, val);
		slider.setPreferredSize(new Dimension(100, 19));
		final JTextField textField = new JTextField(val + "", 5);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		final JLabel displayedValue = new JLabel("%");

		JPanel textAndLabel = new JPanel();
		textAndLabel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		textAndLabel.add(textField);
		textAndLabel.add(displayedValue);

		// adds a listener to the slider
		final MouseAdapter sliderListener = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {

				// modifies the value of the property item
				String newValue = format
						.format(((double) slider.getValue()) / 100);

				propertyItem.changePropertyValue(newValue);
				textField.setText(slider.getValue() + "");
			}
		};

		slider.addMouseListener(sliderListener);

		// adding the listener to the textfield
		final CaretListener textFieldListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent evt) {

				double newVal = Double.NaN;

				try {
					newVal = Double.parseDouble(textField.getText());
				} catch (Exception ex) {
					newVal = Double.NaN;
				}

				if (!Double.isNaN(newVal)) {

					if (newVal > 100) {

						newVal = 100;

						final int fnewVal = (int) newVal;
						final CaretListener thisListener = this;

						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {

								textField.removeCaretListener(thisListener);
								textField.setText(fnewVal + "");
								textField.addCaretListener(thisListener);
							}
						});
					}

					if (newVal < 0) {

						newVal = 0;

						final int fnewVal = (int) newVal;
						final CaretListener thisListener = this;

						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {

								textField.removeCaretListener(thisListener);
								textField.setText(fnewVal + "");
								textField.addCaretListener(thisListener);
							}
						});
					}

					String newValue = format.format(newVal / 100);
					propertyItem.changePropertyValue(newValue);
					slider.setValue((int) newVal);
				}
			}
		};

		textField.addCaretListener(textFieldListener);

		// adds a listener to the slider
		final ChangeListener sliderChangeListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						textField.removeCaretListener(textFieldListener);
						textField.setText(slider.getValue() + "");
						textField.addCaretListener(textFieldListener);
					}
				});
			}
		};

		slider.addChangeListener(sliderChangeListener);

		displayAndSlider.setLayout(new BorderLayout(0, 0));
		displayAndSlider.add(slider, BorderLayout.CENTER);
		displayAndSlider.add(textAndLabel, BorderLayout.NORTH);

		component = displayAndSlider;

		// creates the disposer
		disposer = new Runnable() {

			@Override
			public void run() {

				slider.removeMouseListener(sliderListener);
				slider.removeChangeListener(sliderChangeListener);
				textField.removeCaretListener(textFieldListener);
			}
		};
	}
}
