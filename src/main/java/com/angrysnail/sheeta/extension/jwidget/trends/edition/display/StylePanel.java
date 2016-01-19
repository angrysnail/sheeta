package com.angrysnail.sheeta.extension.jwidget.trends.edition.display;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.library.widgets.IntegerSpinnerWidget;
import com.angrysnail.sheeta.rtdaeditor.jwidget.JWidgetEdition;
import com.angrysnail.sheeta.svgeditor.widgets.ColorChooserWidget;

/**
 * the class used to configure the cursor on the trend box
 * 
 * @author ITRIS, Jordi SUC
 */
public class StylePanel extends JPanel {

	/**
	 * the jwidget edition
	 */
	private JWidgetEdition jwidgetEdition;

	/**
	 * the currently edited element
	 */
	private Element currentElement;

	/**
	 * the color chooser
	 */
	protected ColorChooserWidget colorChooser;

	/**
	 * the listener to the color chooser
	 */
	protected ActionListener colorChooserListener;

	/**
	 * the font size chooser
	 */
	protected IntegerSpinnerWidget fontSizeChooser;

	/**
	 * the listener to the font size chooser
	 */
	protected ActionListener fontSizeChooserListener;

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetEdition
	 *            the jwidget edition
	 */
	public StylePanel(JWidgetEdition jwidgetEdition) {

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

		colorChooser.removeListener(colorChooserListener);
		colorChooser.init(jwidgetEdition.getProperty(element, jwidgetEdition
				.getPropertiesList().get(10)));
		colorChooser.addListener(colorChooserListener);

		fontSizeChooser.removeListener(fontSizeChooserListener);
		fontSizeChooser.init(jwidgetEdition.getProperty(element, jwidgetEdition
				.getPropertiesList().get(11)));
		fontSizeChooser.addListener(fontSizeChooserListener);
	}

	/**
	 * building the panel
	 */
	protected void build() {

		// getting the labels
		String styleLabel = "", backgroundColorLabel = "", fontSizeLabel = "";

		try {
			styleLabel = jwidgetEdition.getBundle().getString("style");
			backgroundColorLabel = jwidgetEdition.getBundle().getString(
					"backgroundColor");
			fontSizeLabel = jwidgetEdition.getBundle().getString("fontSize");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// creating the jlabel for the background color chooser
		JLabel backgroundColorLbl = new JLabel(backgroundColorLabel + " : ");
		backgroundColorLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		// creating the color chooser
		colorChooser = new ColorChooserWidget();

		colorChooserListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jwidgetEdition.setProperty(currentElement, jwidgetEdition
						.getPropertiesList().get(10), colorChooser.getValue(),
						true);
			}
		};

		// creating the jlabel for the font size chooser
		JLabel fontSizeChooserLbl = new JLabel(fontSizeLabel + " : ");
		fontSizeChooserLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		// creating the font size chooser
		fontSizeChooser = new IntegerSpinnerWidget(12, 2, 500, 1);

		fontSizeChooserListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jwidgetEdition.setProperty(currentElement, jwidgetEdition
						.getPropertiesList().get(11),
						fontSizeChooser.getWidgetValue() + "", true);
			}
		};

		// creating the panel used for handling the properties
		TitledBorder border = new TitledBorder(styleLabel);
		setBorder(border);

		GridBagLayout gridBag1 = new GridBagLayout();
		setLayout(gridBag1);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.insets = new Insets(1, 1, 1, 1);
		c1.fill = GridBagConstraints.HORIZONTAL;

		c1.gridwidth = 1;
		c1.anchor = GridBagConstraints.EAST;
		gridBag1.setConstraints(backgroundColorLbl, c1);
		add(backgroundColorLbl);

		c1.fill = GridBagConstraints.NONE;
		c1.gridwidth = GridBagConstraints.REMAINDER;
		c1.anchor = GridBagConstraints.WEST;
		gridBag1.setConstraints(colorChooser, c1);
		add(colorChooser);

		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.gridwidth = 1;
		c1.anchor = GridBagConstraints.EAST;
		gridBag1.setConstraints(fontSizeChooserLbl, c1);
		add(fontSizeChooserLbl);

		c1.gridwidth = GridBagConstraints.REMAINDER;
		c1.anchor = GridBagConstraints.WEST;
		gridBag1.setConstraints(fontSizeChooser, c1);
		add(fontSizeChooser);
	}
}
