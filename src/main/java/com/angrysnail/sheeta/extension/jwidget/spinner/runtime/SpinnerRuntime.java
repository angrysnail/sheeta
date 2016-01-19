package com.angrysnail.sheeta.extension.jwidget.spinner.runtime;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.spinner.edition.SpinnerEdition;
import com.angrysnail.sheeta.extension.jwidget.spinner.runtime.actions.SpinnerSendMeasure;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;

/**
 * the class of the view browser runtime jwidget
 * 
 * @author ITRIS, Jordi SUC
 */
public class SpinnerRuntime extends JWidgetRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "SpinnerWidget";

	/**
	 * the spinner listener
	 */
	private ChangeListener spinnerListener;

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public SpinnerRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		double minValue = 0, maxValue = 0, initialValue = 0, stepSize = 0;

		try {
			minValue = Double.parseDouble(element.getAttribute("minValue"));
			maxValue = Double.parseDouble(element.getAttribute("maxValue"));
			initialValue = Double.parseDouble(element
					.getAttribute("initialValue"));
			stepSize = Double.parseDouble(element.getAttribute("stepSize"));
		} catch (Exception ex) {
		}

		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(initialValue,
				minValue, maxValue, stepSize);
		JSpinner spinner = new JSpinner(spinnerModel);

		// handling the look of the spinner
		JTextField textField = ((JSpinner.NumberEditor) spinner.getEditor())
				.getTextField();
		JWidgetToolkit.handleLook(element, spinner);
		JWidgetToolkit.handleLook(element, textField);
		JWidgetToolkit.handleBackgroundAndBorderLook(element, spinner);
		JWidgetToolkit.handleBackgroundAndBorderLook(element,
				spinner.getEditor());
		JWidgetToolkit.handleBackgroundAndBorderLook(element, textField);
		JWidgetToolkit.handleAlignment(element, textField);

		component = spinner;

		// creating the listener to the spinner
		spinnerListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent evt) {

				for (Action action : getActions()) {

					action.performAction(evt);
				}
			}
		};

		// adding the listener to the spinner
		spinner.addChangeListener(spinnerListener);
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:sendMeasure")) {

				action = new SpinnerSendMeasure(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else {

				action = super.createAction(actionElement);
			}
		}

		return action;
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return SpinnerEdition.class;
	}

	@Override
	public void registerListeners() {

		if (spinnerListener != null) {

			((JSpinner) component).removeChangeListener(spinnerListener);
			((JSpinner) component).addChangeListener(spinnerListener);
		}
	}

	@Override
	public void unregisterListeners() {

		if (spinnerListener != null) {

			((JSpinner) component).removeChangeListener(spinnerListener);
		}
	}

	@Override
	public void dispose() {

		unregisterListeners();
		super.dispose();
	}
}
