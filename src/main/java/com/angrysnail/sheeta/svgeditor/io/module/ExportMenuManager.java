package com.angrysnail.sheeta.svgeditor.io.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;
import com.angrysnail.sheeta.svgeditor.io.managers.export.FileExport;

/**
 * the class of the manager used to build and handle the state of the export
 * menu items of the export menu
 * 
 * @author Jordi SUC
 */
public class ExportMenuManager {

	/**
	 * the menu
	 */
	private JMenu menu = new JMenu();

	/**
	 * the array of the menu items
	 */
	private JMenuItem[] menuItems = new JMenuItem[FileExport.formats.length];

	/**
	 * the constructor of the class
	 */
	public ExportMenuManager() {

		createMenuItems();
	}

	/**
	 * creates the menu items
	 */
	protected void createMenuItems() {

		// creating the export menuitems
		int[] formats = FileExport.formats;
		ActionListener listener = null;

		for (int i = 0; i < formats.length; i++) {

			final int index = i;

			menuItems[i] = new JMenuItem(FileExport.prefixLabels[i]);
			menu.add(menuItems[i]);

			listener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {

					// getting the current handle
					SVGHandle handle = Editor.getEditor().getHandlesManager()
							.getCurrentHandle();

					Editor.getEditor().getIOManager().getFileExportManager()
							.export(handle, index, menuItems[index]);
				}
			};

			menuItems[i].addActionListener(listener);
		}
	}

	/**
	 * @return the menu containing the export menu items
	 */
	public JMenu getMenu() {
		return menu;
	}

	/**
	 * handles the state of the items
	 * 
	 * @param currentHandle
	 *            the current handle
	 */
	public void handleItemsState(SVGHandle currentHandle) {

		for (int i = 0; i < FileExport.formats.length; i++) {

			menuItems[i].setEnabled(currentHandle != null);
		}
	}
}
