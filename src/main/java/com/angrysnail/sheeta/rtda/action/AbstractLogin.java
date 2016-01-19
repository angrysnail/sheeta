package com.angrysnail.sheeta.rtda.action;

import java.awt.Container;
import java.awt.Frame;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JDialog;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtda.animaction.DataEvent;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.widget.LoginDialog;

/**
 * the class of the login command
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class AbstractLogin
		extends
			com.angrysnail.sheeta.rtda.animaction.Action {

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
	public AbstractLogin(SVGPicture picture, String projectName,
			Container parent, JComponent component, Object actionObject,
			Element actionElement, JWidgetRuntime jwidgetRuntime) {

		super(picture, projectName, parent, component, actionObject,
				actionElement, jwidgetRuntime);
	}

	/**
	 * initializes the action
	 */
	protected void initializeAction() {

		// getting the tool tip
		try {
			toolTipText = bundle.getString("tooltip_login");
		} catch (Exception ex) {
		}

		initializeAuthorizationTag();
	}

	@Override
	public Runnable dataChanged(DataEvent evt) {

		super.dataChanged(evt);
		checkIsAuthorized();
		return null;
	}

	@Override
	public void performAction(Object evt) {

		if (isAuthorized) {

			LoginDialog loginDialog = null;
			Container container = component.getTopLevelAncestor();

			if (container instanceof Frame) {

				loginDialog = new LoginDialog((Frame) container);

			} else if (container instanceof JApplet) {

				loginDialog = new LoginDialog(picture.getMainDisplay()
						.getTopLevelFrame());

			} else {

				loginDialog = new LoginDialog((JDialog) container);
			}

			loginDialog.showDialog(component);

			if (loginDialog.hasSucceeded()) {

				picture.getMainDisplay()
						.getUserRights()
						.setCurrentUser(component, loginDialog.getLogin(),
								loginDialog.getPassword());
			}

			loginDialog.disposeDialog();
		}
	}
}
