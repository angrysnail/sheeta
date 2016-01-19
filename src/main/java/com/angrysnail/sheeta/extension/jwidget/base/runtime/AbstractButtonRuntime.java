package com.angrysnail.sheeta.extension.jwidget.base.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonDisconnect;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonLoadData;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonLoadView;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonLogin;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonRecordData;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonRunApplication;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonSendCommand;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonSendMeasure;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonSimpleSendCommand;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.actions.AbstractButtonWriteDataToFile;
import com.angrysnail.sheeta.extension.jwidget.base.runtime.anim.AbstractButtonTextOnState;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.animaction.JWidgetAnimation;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;

/**
 * the class of the jwidget runtime abstract buttons
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class AbstractButtonRuntime extends JWidgetRuntime {

	/**
	 * the button label attribute name
	 */
	protected static String buttonLabelName = "buttonLabel";

	/**
	 * the listener to the button
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
	public AbstractButtonRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		((AbstractButton) component).setText(element
				.getAttribute(buttonLabelName));
		((AbstractButton) component).setSelected(false);

		// creating the action listener
		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				for (Action action : getActions()) {

					action.performAction(e);
				}
			}
		};

		((AbstractButton) component).addActionListener(actionListener);
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:simpleSendCommand")) {

				action = new AbstractButtonSimpleSendCommand(picture,
						projectName, picture.getCanvas(), component, null,
						actionElement, this);

			} else if (tagName.equals("rtda:sendCommand")) {

				action = new AbstractButtonSendCommand(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:sendMeasure")) {

				action = new AbstractButtonSendMeasure(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:loadView")) {

				action = new AbstractButtonLoadView(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:runApplication")) {

				action = new AbstractButtonRunApplication(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:login")) {

				action = new AbstractButtonLogin(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:disconnect")) {

				action = new AbstractButtonDisconnect(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:loadData")) {

				action = new AbstractButtonLoadData(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:recordData")) {

				action = new AbstractButtonRecordData(picture, projectName,
						picture.getCanvas(), component, null, actionElement,
						this);

			} else if (tagName.equals("rtda:writeDataToFile")) {

				action = new AbstractButtonWriteDataToFile(picture,
						projectName, picture.getCanvas(), component, null,
						actionElement, this);

			} else {

				action = super.createAction(actionElement);
			}
		}

		return action;
	}

	@Override
	public JWidgetAnimation createAnimation(Element animationElement) {

		JWidgetAnimation animation = null;

		if (animationElement != null) {

			String tagName = animationElement.getTagName();

			if (tagName.equals("rtda:label")) {

				animation = new AbstractButtonTextOnState(this, component,
						animationElement);
			}
		}

		return animation;
	}

	@Override
	public void registerListeners() {

		if (actionListener != null) {

			((AbstractButton) component).removeActionListener(actionListener);
			((AbstractButton) component).addActionListener(actionListener);
		}
	}

	@Override
	public void unregisterListeners() {

		if (actionListener != null) {

			((AbstractButton) component).removeActionListener(actionListener);
		}
	}

	@Override
	public void dispose() {

		unregisterListeners();

		super.dispose();
	}
}
