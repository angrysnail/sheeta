package com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.tools;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsBundle;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsIcons;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.ComponentsHandler;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.ToolBarComponent;

/**
 * the class of the pause button tool
 * 
 * @author ITRIS, Jordi SUC
 */
public class DisplayCurvesPropertiesTool extends JToggleButton {

	/**
	 * the id
	 */
	public static String id = "DisplayCurvesProperties";

	/**
	 * the listener to the button
	 */
	private ActionListener listener;

	/**
	 * the components handler
	 */
	private ComponentsHandler componentsHandler;

	/**
	 * the constructor of the class
	 * 
	 * @param toolBar
	 *            the tool bar component
	 */
	public DisplayCurvesPropertiesTool(ToolBarComponent toolBar) {

		componentsHandler = toolBar.getComponentsHandler();
		build();
	}

	/**
	 * builds the button
	 */
	protected void build() {

		// getting the label
		String label = TrendsBundle.bundle.getString(id);

		// setting the properties of the button
		setToolTipText(label);

		// setting the icon
		setIcon(TrendsIcons.getIcon(id, false));
		setSelected(componentsHandler.getView().getController()
				.getConfiguration().displayCurvesLegend());

		// adding a listener to the button
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				Point location = new Point(getWidth() / 2, getHeight() / 2);

				SwingUtilities.convertPointToScreen(location,
						DisplayCurvesPropertiesTool.this);
				JComponent destCmp = componentsHandler.getView()
						.getController().getJwidgetRuntime().getPicture()
						.getCanvas();
				SwingUtilities.convertPointFromScreen(location, destCmp);

				componentsHandler.getCurvesPropertiesComponent().setLocation(
						location);
				componentsHandler.getCurvesPropertiesComponent().setVisible(
						isSelected(), null);
			}
		};

		addActionListener(listener);
	}

	/**
	 * disposes the object
	 */
	public void dispose() {// TODO

		removeActionListener(listener);

		listener = null;
		componentsHandler = null;
	}
}
