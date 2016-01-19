package com.angrysnail.sheeta.extension.jwidget.base.runtime.actions;

import java.awt.Container;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtda.AnimationsToolkit;
import com.angrysnail.sheeta.rtda.action.AbstractSimpleSendCommand;
import com.angrysnail.sheeta.rtda.animaction.DataEvent;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;

/**
 * the class of the simple send command action
 * 
 * @author ITRIS, Jordi SUC
 */
public class ButtonGroupSimpleSendCommand extends AbstractSimpleSendCommand {

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture this action is linked to
	 * @param projectName
	 *            the name of the project this action is linked to
	 * @param parent
	 *            the top level container
	 * @param component
	 *            the component on which the action is registered
	 * @param actionObject
	 *            the object to which the action applies, if it is not the
	 *            provided component
	 * @param actionElement
	 *            the dom element defining the action
	 * @param jwidgetRuntime
	 *            the jwidget runtime object, if this action is linked to a
	 *            jwidget
	 */
	public ButtonGroupSimpleSendCommand(SVGPicture picture, String projectName,
			Container parent, JComponent component, Object actionObject,
			Element actionElement, JWidgetRuntime jwidgetRuntime) {

		super(picture, projectName, parent, component, actionObject,
				actionElement, jwidgetRuntime);

		initializeAction();
	}

	@Override
	public Runnable dataChanged(DataEvent evt) {

		super.dataChanged(evt);

		if (isEntitled()) {

			final Map<String, Object> newMap = evt.getDataNameToValue();

			if (isAuthorized
					&& newMap.containsKey(actionElement
							.getAttribute(tagToWriteName))) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {

						String newTagToWriteValue = "";
						Object obj = newMap.get(actionElement
								.getAttribute(tagToWriteName));

						if (obj != null) {

							newTagToWriteValue = obj.toString();
						}

						if (!newTagToWriteValue.equals("")
								&& newTagToWriteValue.equals(AnimationsToolkit
										.normalizeEnumeratedValue(actionElement
												.getAttribute(valueName)))) {

							jwidgetRuntime.unregisterListeners();
							((AbstractButton) component).setSelected(true);
							jwidgetRuntime.registerListeners();
						}
					}
				});
			}

			jwidgetRuntime.refreshComponentState();
		}

		return null;
	}
}
