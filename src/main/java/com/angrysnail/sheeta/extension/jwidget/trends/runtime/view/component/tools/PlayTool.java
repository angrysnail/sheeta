package com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsBundle;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.TrendsIcons;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.configuration.TrendsConfiguration;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.ComponentsHandler;
import com.angrysnail.sheeta.extension.jwidget.trends.runtime.view.component.ToolBarComponent;

/**
 * the class of the play button tool
 * 
 * @author ITRIS, Jordi SUC
 */
public class PlayTool extends JToggleButton {

	/**
	 * the id
	 */
	public static final String id = "Play";

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
	public PlayTool(ToolBarComponent toolBar) {

		this.componentsHandler = toolBar.getComponentsHandler();
		this.configuration = componentsHandler.getView().getController()
				.getConfiguration();
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

		// adding a listener to the button
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				if (isSelected()) {

					componentsHandler.setCurrentAction(id, true);
					configuration.setCurrentSubMode(TrendsConfiguration.UPDATE);
				}
			}
		};

		// initializing the state of the button
		modeOrSubModeChanged();
	}

	/**
	 * disposes the object
	 */
	public void dispose() {// TODO

		removeActionListener(listener);

		listener = null;
		componentsHandler = null;
		configuration = null;
	}

	/**
	 * called when the mode or the submode has changed
	 */
	public void modeOrSubModeChanged() {

		removeActionListener(listener);

		if (configuration.getCurrentMode() == TrendsConfiguration.HISTORY_MODE) {

			setEnabled(false);

		} else {

			setEnabled(true);

			if (configuration.getCurrentSubMode() == TrendsConfiguration.UPDATE) {

				setSelected(true);
			}
		}

		addActionListener(listener);
	}
}
