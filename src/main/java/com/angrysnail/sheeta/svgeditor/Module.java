package com.angrysnail.sheeta.svgeditor;

import java.util.Collection;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JMenuItem;

import com.angrysnail.sheeta.svgeditor.actions.popup.PopupItem;

/**
 * the interface that each module must implement
 * 
 * @author ITRIS, Jordi SUC
 *
 */
public interface Module {

	/**
	 * @return a map associating a menu item id to its menu item object
	 */
	public HashMap<String, JMenuItem> getMenuItems();

	/**
	 * Returns the list of the popup items
	 * 
	 * @return the list of the popup items
	 */
	public Collection<PopupItem> getPopupItems();

	/**
	 * @return a map associating a tool item id to its tool item object
	 */
	public HashMap<String, AbstractButton> getToolItems();

	/**
	 * initializes the module
	 */
	public void initialize();
}
