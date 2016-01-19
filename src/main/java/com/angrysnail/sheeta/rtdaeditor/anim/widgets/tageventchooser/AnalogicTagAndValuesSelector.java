package com.angrysnail.sheeta.rtdaeditor.anim.widgets.tageventchooser;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.angrysnail.sheeta.library.widgets.DoubleSpinnerWidget;
import com.angrysnail.sheeta.library.widgets.EqualityChooserWidget;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventsManager;
import com.angrysnail.sheeta.rtda.toolkit.TagToolkit;
import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the class of the selector for the analogic tag and its condition
 * 
 * @author Jordi SUC
 */
public class AnalogicTagAndValuesSelector extends JPanel {

	/**
	 * the tag event chooser component
	 */
	protected TagEventChooser tagEventChooser;

	/**
	 * the radio button
	 */
	protected JRadioButton radioButton;

	/**
	 * the tag chooser widget
	 */
	protected TagChooser tagChooserWidget;

	/**
	 * the equality chooser widget
	 */
	protected EqualityChooserWidget equalityChooserWidget;

	/**
	 * the spinner used to choose the value for the condition value
	 */
	protected DoubleSpinnerWidget doubleSpinner;

	/**
	 * the jlabels
	 */
	private JLabel tagValuesLbl;

	/**
	 * the condition panel
	 */
	private JPanel conditionPanel;

	/**
	 * the constructor of the class
	 * 
	 * @param tagEventChooser
	 *            the tag event chooser
	 * @param radioButton
	 *            the radio button
	 */
	public AnalogicTagAndValuesSelector(TagEventChooser tagEventChooser,
			JRadioButton radioButton) {

		this.tagEventChooser = tagEventChooser;
		this.radioButton = radioButton;
		build();
	}

	/**
	 * builds the new component
	 */
	protected void build() {

		// getting the labels
		String conditionLabel = ResourcesManager.bundle
				.getString("rtdaanim_condition");
		String tagValuesLabel = ResourcesManager.bundle
				.getString("rtdaanim_anatagValueCondition");

		// creating the jlabels
		tagValuesLbl = new JLabel(tagValuesLabel + " : ");
		tagValuesLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		// creating the tag chooser
		tagChooserWidget = new TagChooser(TagToolkit.ANALOGIC);

		// creating the equality chooser
		equalityChooserWidget = new EqualityChooserWidget();

		// creating the tag value chooser
		doubleSpinner = new DoubleSpinnerWidget(0, -1000000000, 1000000000, 1,
				false);

		// creating the condition panel
		conditionPanel = new JPanel();
		conditionPanel
				.setBorder(new CompoundBorder(new EmptyBorder(0, 25, 0, 0),
						new TitledBorder(conditionLabel)));

		GridBagLayout layout = new GridBagLayout();
		conditionPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 1, 1, 1);

		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(tagValuesLbl, c);
		conditionPanel.add(tagValuesLbl);

		c.gridx = 1;
		layout.setConstraints(equalityChooserWidget, c);
		conditionPanel.add(equalityChooserWidget);

		c.gridx = 2;
		c.weightx = 50;
		layout.setConstraints(doubleSpinner, c);
		conditionPanel.add(doubleSpinner);

		// building the component
		layout = new GridBagLayout();
		setLayout(layout);
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 1, 1, 1);

		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(radioButton, c);
		add(radioButton);

		c.gridx = 1;
		c.weightx = 50;
		layout.setConstraints(tagChooserWidget, c);
		add(tagChooserWidget);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		layout.setConstraints(conditionPanel, c);
		add(conditionPanel);
	}

	/**
	 * initializes the widgets with the new provided value
	 * 
	 * @param newValue
	 *            the new value
	 */
	protected void init(String newValue) {

		// getting the tag name
		newValue = newValue.trim();
		String[] splitValues = newValue.split(TagEventsManager.separatorRegex);

		if (splitValues != null && splitValues.length == 4) {

			// getting the tag name
			String tagName = splitValues[1];

			// getting the equality mode
			String equalityMode = splitValues[2];

			// getting the tag value
			String tagValue = splitValues[3];

			tagChooserWidget.init(tagName);
			equalityChooserWidget.init(equalityMode);
			doubleSpinner.init(tagValue);

		} else {

			tagChooserWidget.init("");
			equalityChooserWidget.init("");
			doubleSpinner.init("");
		}
	}

	/**
	 * @return the current value
	 */
	protected String getValue() {

		String value = "";
		String tagValue = tagChooserWidget.getCurrentValue();

		if (tagValue == null) {

			tagValue = "";
		}

		value = TagEventsManager.analogicKeyword + TagEventsManager.separator
				+ tagValue + TagEventsManager.separator
				+ equalityChooserWidget.getValue() + TagEventsManager.separator
				+ doubleSpinner.getWidgetValue();

		return value;
	}

	@Override
	public void setEnabled(boolean enable) {

		tagChooserWidget.setEnabled(enable);
		equalityChooserWidget.setEnabled(enable);
		doubleSpinner.setEnabled(enable);
		conditionPanel.setEnabled(enable);
		tagValuesLbl.setEnabled(enable);

		super.setEnabled(enable);
	}
}
