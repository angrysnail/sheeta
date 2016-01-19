package com.angrysnail.sheeta.rtda.animaction;

import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.angrysnail.sheeta.library.Toolkit;
import com.angrysnail.sheeta.rtda.action.svg.Disconnect;
import com.angrysnail.sheeta.rtda.action.svg.LoadData;
import com.angrysnail.sheeta.rtda.action.svg.LoadView;
import com.angrysnail.sheeta.rtda.action.svg.Login;
import com.angrysnail.sheeta.rtda.action.svg.RecordData;
import com.angrysnail.sheeta.rtda.action.svg.RunApplication;
import com.angrysnail.sheeta.rtda.action.svg.SendCommand;
import com.angrysnail.sheeta.rtda.action.svg.SendMeasure;
import com.angrysnail.sheeta.rtda.action.svg.SimpleSendCommand;
import com.angrysnail.sheeta.rtda.action.svg.WriteDataToFile;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventDisconnect;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventLoadData;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventLoadView;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventLogin;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventRecordData;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventRunApplication;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventSendCommand;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventSendMeasure;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventSimpleSendCommand;
import com.angrysnail.sheeta.rtda.action.tagevent.TagEventWriteDataToFile;
import com.angrysnail.sheeta.rtda.animations.AttributeOnInterval;
import com.angrysnail.sheeta.rtda.animations.AttributeOnMeasure;
import com.angrysnail.sheeta.rtda.animations.AttributeOnState;
import com.angrysnail.sheeta.rtda.animations.BarGraph;
import com.angrysnail.sheeta.rtda.animations.ColorOnMeasure;
import com.angrysnail.sheeta.rtda.animations.ColorOnState;
import com.angrysnail.sheeta.rtda.animations.CurrentUser;
import com.angrysnail.sheeta.rtda.animations.Label;
import com.angrysnail.sheeta.rtda.animations.MeasureSymbol;
import com.angrysnail.sheeta.rtda.animations.MeasureText;
import com.angrysnail.sheeta.rtda.animations.Rotation;
import com.angrysnail.sheeta.rtda.animations.Scale;
import com.angrysnail.sheeta.rtda.animations.StateSymbol;
import com.angrysnail.sheeta.rtda.animations.Translation;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.resources.RtdaResources;

/**
 * the listeners and actions builder
 * 
 * @author ITRIS, Jordi SUC
 */
public class ListenerActionBuilder {

	/**
	 * the animations and actions document
	 */
	protected static Document rtdaAnimationsAndActionsDocument = RtdaResources
			.getXMLDocument("rtdaAnimationsAndActions.xml");

	/**
	 * the set containing the name of the supported animation nodes
	 */
	protected static Set<String> animationNames = new HashSet<String>();

	/**
	 * the set containing the name of the supported action nodes
	 */
	protected static Set<String> actionNames = new HashSet<String>();

	static {

		// getting all the animation and actions nodes in the document and
		// computing their names
		NodeList animations = rtdaAnimationsAndActionsDocument
				.getElementsByTagName("animation");
		NodeList actions = rtdaAnimationsAndActionsDocument
				.getElementsByTagName("action");

		for (int i = 0; i < animations.getLength(); i++) {

			animationNames.add(((Element) animations.item(i))
					.getAttribute("name"));
		}

		String name = "";

		for (int i = 0; i < actions.getLength(); i++) {

			name = ((Element) actions.item(i)).getAttribute("name");
			actionNames.add(name);
		}
	}

	/**
	 * returns whether the given node name is the name of an animation or an
	 * action name
	 * 
	 * @param nodeName
	 *            the name of an animation or an action
	 * @return whether the given node name is the name of an animation or an
	 *         action name
	 */
	public static boolean isAnimationNodeSupported(String nodeName) {

		return animationNames.contains(nodeName);
	}

	/**
	 * returns whether the node name corresponds to an action or not
	 * 
	 * @param nodeName
	 *            the name of a node
	 * @return whether the node name corresponds to an action or not
	 */
	public static boolean isActionNode(String nodeName) {

		return actionNames.contains(nodeName);
	}

	/**
	 * creates a data changed listener
	 * 
	 * @param picture
	 *            a svg picture
	 * @param animationElement
	 *            the animation element
	 * @return the creates data changed listener
	 */
	public static DataChangedListener createDataChangedListener(
			SVGPicture picture, Element animationElement) {

		DataChangedListener dataChangedListener = null;

		if (picture != null && animationElement != null) {

			String nodeName = animationElement.getNodeName();

			if (nodeName.equals("rtda:colorOnState")) {

				dataChangedListener = new ColorOnState(picture,
						animationElement);

			} else if (nodeName.equals("rtda:colorOnMeasure")) {

				dataChangedListener = new ColorOnMeasure(picture,
						animationElement);

			} else if (nodeName.equals("rtda:bargraph")) {

				dataChangedListener = new BarGraph(picture, animationElement);

			} else if (nodeName.equals("rtda:attributeOnState")) {

				dataChangedListener = new AttributeOnState(picture,
						animationElement);

			} else if (nodeName.equals("rtda:attributeOnInterval")) {

				dataChangedListener = new AttributeOnInterval(picture,
						animationElement);

			} else if (nodeName.equals("rtda:attributeOnMeasure")) {

				dataChangedListener = new AttributeOnMeasure(picture,
						animationElement);

			} else if (nodeName.equals("rtda:translation")) {

				dataChangedListener = new Translation(picture, animationElement);

			} else if (nodeName.equals("rtda:rotation")) {

				dataChangedListener = new Rotation(picture, animationElement);

			} else if (nodeName.equals("rtda:scale")) {

				dataChangedListener = new Scale(picture, animationElement);

			} else if (nodeName.equals("rtda:text")) {

				dataChangedListener = new com.angrysnail.sheeta.rtda.animations.Text(
						picture, animationElement);

			} else if (nodeName.equals("rtda:label")) {

				dataChangedListener = new Label(picture, animationElement);

			} else if (nodeName.equals("rtda:measureText")) {

				dataChangedListener = new MeasureText(picture, animationElement);

			} else if (nodeName.equals("rtda:stateSymbol")) {

				dataChangedListener = new StateSymbol(picture, animationElement);

			} else if (nodeName.equals("rtda:measureSymbol")) {

				dataChangedListener = new MeasureSymbol(picture,
						animationElement);

			} else if (nodeName.equals("rtda:user")) {

				dataChangedListener = new CurrentUser(picture, animationElement);
			}
		}

		return dataChangedListener;
	}

	/**
	 * creates an returns the action corresponding to the given action element
	 * 
	 * @param picture
	 *            the svg picture this action is linked to
	 * @param projectName
	 *            the name of the project this action is linked to
	 * @param component
	 *            the component on which the action is registered
	 * @param actionObject
	 *            the object to which the action applies, if it is not the
	 *            provided component
	 * @param actionElement
	 *            the dom element defining the action
	 * @return the action corresponding to the given action element
	 */
	public static Action createAction(SVGPicture picture, String projectName,
			JComponent component, Object actionObject, Element actionElement) {

		Action action = null;

		if (component != null && actionElement != null) {

			String nodeName = actionElement.getNodeName();

			// checking if the action denotes a tag event action
			Element parentElement = (Element) actionElement.getParentNode();
			boolean isTagEvent = parentElement.getNodeName().equals(
					Toolkit.svgNodeName);

			if (nodeName.equals("rtda:simpleSendCommand")) {

				if (isTagEvent) {

					action = new TagEventSimpleSendCommand(picture,
							projectName, component.getTopLevelAncestor(),
							component, actionObject, actionElement, null);

				} else {

					action = new SimpleSendCommand(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:sendCommand")) {

				if (isTagEvent) {

					action = new TagEventSendCommand(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new SendCommand(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:sendMeasure")) {

				if (isTagEvent) {

					action = new TagEventSendMeasure(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new SendMeasure(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:loadView")) {

				if (isTagEvent) {

					action = new TagEventLoadView(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new LoadView(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:runApplication")) {

				if (isTagEvent) {

					action = new TagEventRunApplication(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new RunApplication(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:login")) {

				if (isTagEvent) {

					action = new TagEventLogin(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new Login(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:disconnect")) {

				if (isTagEvent) {

					action = new TagEventDisconnect(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new Disconnect(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:loadData")) {

				if (isTagEvent) {

					action = new TagEventLoadData(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new LoadData(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:recordData")) {

				if (isTagEvent) {

					action = new TagEventRecordData(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new RecordData(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:writeDataToFile")) {

				if (isTagEvent) {

					action = new TagEventWriteDataToFile(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);

				} else {

					action = new WriteDataToFile(picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement, null);
				}

			} else if (nodeName.equals("rtda:class")) {

				// retrieving the class name in the action element
				String className = actionElement
						.getAttribute(CustomAction.classAttributeName);

				// getting the class corresponding to the given class name
				Class<?> theClass = ActionsLoader.getClass(CustomAction.class,
						picture.getCanvas().getProjectFile(), className, false);

				if (theClass != null) {

					// creating an instance of the class
					Class<?>[] classParam = {SVGPicture.class, String.class,
							Container.class, JComponent.class, Object.class,
							Element.class};
					Object[] params = {picture, projectName,
							component.getTopLevelAncestor(), component,
							actionObject, actionElement};

					try {
						action = (com.angrysnail.sheeta.rtda.animaction.Action) theClass
								.getConstructor(classParam).newInstance(params);
					} catch (Exception ex) {
					}
				}
			}
		}

		return action;
	}
}
