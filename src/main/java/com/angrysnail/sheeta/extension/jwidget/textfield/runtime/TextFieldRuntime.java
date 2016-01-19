package com.angrysnail.sheeta.extension.jwidget.textfield.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.textfield.edition.TextFieldEdition;
import com.angrysnail.sheeta.extension.jwidget.textfield.runtime.actions.TextFieldSendString;
import com.angrysnail.sheeta.extension.jwidget.textfield.runtime.actions.TextFieldSimpleSendCommand;
import com.angrysnail.sheeta.extension.jwidget.textfield.runtime.anim.TextFieldCurrentUser;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.animaction.JWidgetAnimation;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;

/**
 * the class of the view browser runtime jwidget
 * 
 * @author ITRIS, Jordi SUC
 */
public class TextFieldRuntime extends JWidgetRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "TextFieldWidget";

	/**
	 * the text field listener
	 */
	private Listener listener;

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public TextFieldRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		JTextField textField = new JTextField();

		component = textField;

		// handling the look of the text field
		JWidgetToolkit.handleLook(element, component);
		JWidgetToolkit.handleBackgroundAndBorderLook(element, component);
		JWidgetToolkit.handleAlignment(element, textField);

		// creating the listener
		listener = new Listener();

		((JTextField) component).addActionListener(listener);
		((JTextField) component).addFocusListener(listener);
		((JTextField) component).addCaretListener(listener);
	}

	@Override
	public JWidgetAnimation createAnimation(Element animationElement) {

		JWidgetAnimation animation = null;

		if (animationElement != null) {

			String tagName = animationElement.getTagName();

			if (tagName.equals("rtda:user")) {

				animation = new TextFieldCurrentUser(this, component,
						animationElement);
			}
		}

		return animation;
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:simpleSendCommand")) {

				action = new TextFieldSimpleSendCommand(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:sendString")) {

				action = new TextFieldSendString(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);
			}
		}

		return action;
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return TextFieldEdition.class;
	}

	@Override
	public void refreshComponentState() {

		boolean enabled = !allActionsInactive();

		if (!enabled && getAnimations().size() > 0) {

			((JTextField) component).setEditable(false);
			component.setEnabled(true);

		} else if (enabled) {

			((JTextField) component).setEditable(true);
			component.setEnabled(true);

		} else {

			component.setEnabled(false);
		}
	}

	@Override
	public void registerListeners() {

		if (listener != null) {

			((JTextField) component).removeActionListener(listener);
			((JTextField) component).removeFocusListener(listener);
			((JTextField) component).removeCaretListener(listener);

			((JTextField) component).addActionListener(listener);
			((JTextField) component).addFocusListener(listener);
			((JTextField) component).addCaretListener(listener);
		}
	}

	@Override
	public void unregisterListeners() {

		if (listener != null) {

			((JTextField) component).removeActionListener(listener);
			((JTextField) component).removeFocusListener(listener);
			((JTextField) component).removeCaretListener(listener);
		}
	}

	@Override
	public void dispose() {

		unregisterListeners();
		super.dispose();
	}

	/**
	 * the class of the listener to the textfield
	 * 
	 * @author Jordi SUC
	 */
	protected class Listener extends FocusAdapter
			implements
				CaretListener,
				ActionListener {

		/**
		 * whether the textfield has been updated
		 */
		protected boolean updated = false;

		@Override
		public void caretUpdate(CaretEvent e) {

			updated = true;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (updated) {

				for (Action action : getActions()) {

					action.performAction(e);
				}

				updated = false;
			}
		}

		@Override
		public void focusLost(FocusEvent e) {

			if (updated) {

				for (Action action : getActions()) {

					action.performAction(e);
				}

				updated = false;
			}
		}
	}
}
