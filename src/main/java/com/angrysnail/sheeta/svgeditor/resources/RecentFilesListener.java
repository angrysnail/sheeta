package com.angrysnail.sheeta.svgeditor.resources;

import java.util.LinkedList;

/**
 * the class of the listener to the recent files
 * 
 * @author Jordi SUC
 */
public abstract class RecentFilesListener {

	/**
	 * notifies that the recent file paths list has changed
	 * 
	 * @param filesPathsList
	 *            the new recent file paths list
	 */
	public abstract void recentFilesListChanged(
			LinkedList<String> filesPathsList);
}
