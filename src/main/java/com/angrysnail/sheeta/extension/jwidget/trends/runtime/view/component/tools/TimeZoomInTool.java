package com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsBundle;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsIcons;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.configuration.TrendsConfiguration;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.ComponentsHandler;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.ToolBarComponent;

/**
 * the class of the tool used to decrease the displayed time on the horizontal
 * axis
 * 
 * @author ITRIS, Jordi SUC
 */
public class TimeZoomInTool extends JButton {

	/**
	 * the id
	 */
	private String id = "TimeZoomIn";

	/**
	 * the components handler
	 */
	private ComponentsHandler componentsHandler;

	/**
	 * the listener to the button
	 */
	private ActionListener listener;

	/**
	 * the configuration object
	 */
	private TrendsConfiguration configuration;

	/**
	 * the constructor of the class
	 * 
	 * @param toolBar
	 *            the tool bar component
	 */
	public TimeZoomInTool(ToolBarComponent toolBar) {

		this.componentsHandler = toolBar.getComponentsHandler();
		this.configuration = componentsHandler.getView().getController()
				.getConfiguration();

		build();
	}

	/**
	 * builds the button
	 */
	protected void build() {

		// setting the properties of the button
		setToolTipText(TrendsBundle.bundle.getString(id));

		// setting the icon
		setIcon(TrendsIcons.getIcon(id, false));

		// adding a listener to the button
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				componentsHandler.setCurrentAction(id, true);

				// getting the horizontal axis duration
				long duration = configuration.getHorizontalAxisDuration();
				long newDuration = (long) Math.floor(duration / 2);

				if (newDuration < 2) {

					newDuration = 2;
				}

				configuration.setHorizontalAxisDuration(newDuration);
			}
		};

		addActionListener(listener);
	}

	/**
	 * disposes the object
	 */
	public void dispose() {

		removeActionListener(listener);

		listener = null;
		componentsHandler = null;
		configuration = null;
	}
}
