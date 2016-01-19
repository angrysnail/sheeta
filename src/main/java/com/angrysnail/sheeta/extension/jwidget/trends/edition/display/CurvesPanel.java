package com.angrysnail.sheeta.extension.jwidget.trends.edition.display;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetEdition;

/**
 * the class used to configure the cursor on the trend box
 * 
 * @author ITRIS, Jordi SUC
 */
public class CurvesPanel extends JPanel {

	/**
	 * the jwidget edition
	 */
	private JWidgetEdition jwidgetEdition;

	/**
	 * the currently edited element
	 */
	private Element currentElement;

	/**
	 * the display check box
	 */
	private JCheckBox displayCurveLegendsCheckBox;

	/**
	 * the action listener
	 */
	private ActionListener checkBoxActionListener;

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetEdition
	 *            the jwidget edition
	 */
	public CurvesPanel(JWidgetEdition jwidgetEdition) {

		this.jwidgetEdition = jwidgetEdition;
		build();
	}

	/**
	 * initializes the panel
	 * 
	 * @param element
	 *            an element
	 */
	public void initialize(Element element) {

		this.currentElement = element;

		// initializing the value for each widget
		displayCurveLegendsCheckBox
				.removeActionListener(checkBoxActionListener);

		try {
			displayCurveLegendsCheckBox.setSelected(Boolean
					.parseBoolean(jwidgetEdition.getProperty(element,
							jwidgetEdition.getPropertiesList().get(8))));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		displayCurveLegendsCheckBox.addActionListener(checkBoxActionListener);
	}

	/**
	 * building the panel
	 */
	protected void build() {

		// getting the labels
		String curvesLabel = "", displayCurveLegendsLabel = "";

		try {
			curvesLabel = jwidgetEdition.getBundle().getString("curves");
			displayCurveLegendsLabel = jwidgetEdition.getBundle().getString(
					"displayCurveLegends");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// creating the checkbox
		displayCurveLegendsCheckBox = new JCheckBox(displayCurveLegendsLabel);
		displayCurveLegendsCheckBox.setHorizontalAlignment(SwingConstants.LEFT);

		// creating the listener to the checkbox
		checkBoxActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jwidgetEdition.setProperty(currentElement, jwidgetEdition
						.getPropertiesList().get(8), Boolean
						.toString(displayCurveLegendsCheckBox.isSelected()),
						true);
			}
		};

		// creating the panel used for handling the properties
		TitledBorder border = new TitledBorder(curvesLabel);
		setBorder(border);

		GridBagLayout gridBag1 = new GridBagLayout();
		setLayout(gridBag1);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(1, 1, 1, 1);
		c1.fill = GridBagConstraints.NONE;

		c1.gridwidth = 1;
		c1.anchor = GridBagConstraints.WEST;
		c1.weightx = 50;
		gridBag1.setConstraints(displayCurveLegendsCheckBox, c1);
		add(displayCurveLegendsCheckBox);
	}
}
