package com.angrysnail.sheeta.extension.jwidget.combo.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.combo.edition.ComboEdition;
import com.angrysnail.sheeta.extension.jwidget.combo.runtime.actions.ComboLoadView;
import com.angrysnail.sheeta.extension.jwidget.combo.runtime.actions.ComboSendMeasure;
import com.angrysnail.sheeta.extension.jwidget.combo.runtime.actions.ComboSimpleSendCommand;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;

/**
 * the class of the view browser runtime jwidget
 * 
 * @author ITRIS, Jordi SUC
 */
public class ComboRuntime extends JWidgetRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "ComboWidget";

	/**
	 * the listener to the combo
	 */
	protected ActionListener actionListener;

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public ComboRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		JComboBox combo = new JComboBox();
		component = combo;

		// handling the look of the combo
		JWidgetToolkit.handleLook(element, combo);

		// creating the action listener
		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Action action : getActions()) {

					action.performAction(e);
				}
			}
		};

		((JComboBox) component).addActionListener(actionListener);
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:simpleSendCommand")) {

				action = new ComboSimpleSendCommand(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:sendMeasure")) {

				action = new ComboSendMeasure(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:loadView")) {

				action = new ComboLoadView(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else {

				action = super.createAction(actionElement);
			}
		}

		return action;
	}

	@Override
	public void registerListeners() {

		if (actionListener != null) {

			((JComboBox) component).removeActionListener(actionListener);
			((JComboBox) component).addActionListener(actionListener);
		}
	}

	@Override
	public void unregisterListeners() {

		if (actionListener != null) {

			((JComboBox) component).removeActionListener(actionListener);
		}
	}

	@Override
	public void dispose() {

		unregisterListeners();
		super.dispose();
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ComboEdition.class;
	}
}
