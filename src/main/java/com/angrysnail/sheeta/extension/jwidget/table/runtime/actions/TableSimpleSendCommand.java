package com.angrysnail.sheeta.extension.jwidget.table.runtime.actions;

import java.awt.Container;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.table.runtime.TableRuntime;
import com.angrysnail.sheeta.extension.jwidget.table.runtime.TableWidget;
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
public class TableSimpleSendCommand extends AbstractSimpleSendCommand {

	/**
	 * the row and col indices corresponding to the cell that is handled by this
	 * action
	 */
	private int rowIndex = 0, colIndex = 0;

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
	public TableSimpleSendCommand(SVGPicture picture, String projectName,
			Container parent, JComponent component, Object actionObject,
			Element actionElement, JWidgetRuntime jwidgetRuntime) {

		super(picture, projectName, parent, component, actionObject,
				actionElement, jwidgetRuntime);

		initializeAction();
	}

	@Override
	protected void initializeAction() {

		// getting the cell coordinates in the table
		rowIndex = TableRuntime.getRow(sourceId);
		colIndex = TableRuntime.getCol(sourceId);

		super.initializeAction();

		// setting whether the cell linked to this action can be edited or not
		((TableWidget) component).setCellEditable(rowIndex, colIndex,
				isAuthorized);
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

						String newValue = "";
						Object obj = getData(actionElement
								.getAttribute(tagToWriteName));

						if (obj != null) {

							newValue = obj.toString();
						}

						jwidgetRuntime.unregisterListeners();
						((TableWidget) component).getModel().setValueAt(
								newValue, rowIndex, colIndex);
						jwidgetRuntime.registerListeners();
					}
				});
			}

			jwidgetRuntime.refreshComponentState();
		}

		return null;
	}

	@Override
	public void performAction(Object evt) {

		if (isEntitled() && isAuthorized && showConfirmationDialog()) {

			// getting the current value of the tag
			Object obj = getData(actionElement.getAttribute(tagToWriteName));
			String currentTagToWriteValue = "";

			if (obj != null) {

				currentTagToWriteValue = obj.toString();
			}

			// getting the new value
			Object newObj = ((TableWidget) component).getModel().getValueAt(
					rowIndex, colIndex);
			String newValue = "";

			if (newObj != null) {

				newValue = newObj.toString();
			}

			// setting the new tag value
			newValue = AnimationsToolkit.normalizeEnumeratedValue(newValue);
			putTagValue(actionElement.getAttribute(tagToWriteName), newValue);

			handleReturnToInitialValue(currentTagToWriteValue);
		}
	}
}
