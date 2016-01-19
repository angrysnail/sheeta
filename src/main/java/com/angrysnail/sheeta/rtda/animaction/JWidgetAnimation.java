package com.angrysnail.sheeta.rtda.animaction;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JComponent;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.test.TestTagInformation;

/**
 * the class of a jwidget animation listener
 * 
 * @author Jordi SUC
 */
public class JWidgetAnimation implements ListenerAction {

	/**
	 * the animation element
	 */
	protected Element animationElement;

	/**
	 * the associated jwidget runtime object
	 */
	protected JWidgetRuntime jwidgetRuntime;

	/**
	 * the component to animate
	 */
	protected JComponent component;

	/**
	 * the set of the names of the data
	 */
	protected Set<String> dataNames = new CopyOnWriteArraySet<String>();

	/**
	 * the map associating a tag name to its pieces of information
	 */
	protected Map<String, TestTagInformation> dataNamesToInformation = new ConcurrentHashMap<String, TestTagInformation>();

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetRuntime
	 *            the associated jwidget runtime object
	 * @param component
	 *            the component to animate
	 * @param animationElement
	 *            the animation element
	 */
	public JWidgetAnimation(JWidgetRuntime jwidgetRuntime,
			JComponent component, Element animationElement) {

		this.jwidgetRuntime = jwidgetRuntime;
		this.component = component;
		this.animationElement = animationElement;

		jwidgetRuntime.addAnimation(this);
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#initializeWhenCanvasDisplayed()
	 */
	@Override
	public void initializeWhenCanvasDisplayed() {
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#dataChanged(com.angrysnail.sheeta.rtda.animaction.DataEvent)
	 */
	@Override
	public Runnable dataChanged(DataEvent evt) {
		return null;
	}

	/**
	 * returns the value corresponding to the provided tag name
	 * 
	 * @param tagName
	 *            a tag name
	 * @return the value corresponding to the provided tag name
	 */
	public Object getData(String tagName) {

		return jwidgetRuntime.getPicture().getMainDisplay()
				.getAnimationsHandler().getData(tagName);
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#dispose()
	 */
	@Override
	public void dispose() {

		jwidgetRuntime.removeAnimation(this);
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#getDataNames()
	 */
	@Override
	public Set<String> getDataNames() {

		return dataNames;
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#getDataNamesToInformation()
	 */
	@Override
	public Map<String, TestTagInformation> getDataNamesToInformation() {

		return dataNamesToInformation;
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#getTooltipText()
	 */
	@Override
	public String getTooltipText() {

		return "";
	}

	@Override
	public SVGPicture getPicture() {

		return jwidgetRuntime.getPicture();
	}

	/**
	 * @see com.angrysnail.sheeta.rtda.animaction.ListenerAction#invalidMarkersAllowed()
	 */
	@Override
	public boolean invalidMarkersAllowed() {

		return false;
	}
}
