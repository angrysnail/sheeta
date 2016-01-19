/*
 * Created on 10 mars 2005
 */
package com.angrysnail.sheeta.rtda.widget;

/**
 * the class used to be notified when the value of the number chooser changes
 * 
 * @author ITRIS, Jordi SUC
 */
public interface NumberChooserListener {

	/**
	 * notifies that the value has changed
	 * 
	 * @param newValue
	 *            the new value
	 */
	public void valueChanged(String newValue);

}
