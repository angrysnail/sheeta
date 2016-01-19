package com.angrysnail.sheeta.svgeditor.display.selection;

import java.util.Set;

import org.w3c.dom.Element;

/**
 * the class of the listener to the selection changes
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class SelectionChangedListener {

	/**
	 * notifies the object registering this listener that the selection has
	 * changed on the svg canvas associated to the selection manager
	 * 
	 * @param selectedElements
	 *            the setof the selected elements
	 */
	public abstract void selectionChanged(Set<Element> selectedElements);
}
