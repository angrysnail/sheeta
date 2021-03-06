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
 * the class of the tool used to modify the displayed time on the horizontal
 * axis and the vertical scale by means of the mouse
 * 
 * @author ITRIS, Jordi SUC
 */
public class VerticalZoomInitialTool extends JButton {

	/**
	 * the tool id
	 */
	public static String id = "VerticalZoomInitial";

	/**
	 * the listener to the button
	 */
	private ActionListener listener;

	/**
	 * the configuration object
	 */
	private TrendsConfiguration configuration;

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
	public VerticalZoomInitialTool(ToolBarComponent toolBar) {

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

				if (configuration.getCurrentMode() == TrendsConfiguration.REAL_TIME_MODE
						&& configuration.getCurrentSubMode() == TrendsConfiguration.UPDATE) {

					configuration.setCurrentSubMode(TrendsConfiguration.SCROLL);
				}

				configuration.setVerticalZoomFactor(1.0, false);
				configuration.setVerticalZoomOrigin(0);

				// setting the horizontal axis duration
				configuration.setHorizontalAxisDuration(configuration
						.getInitialHorizontalAxisDuration());
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
		configuration = null;
		componentsHandler = null;
	}
}
