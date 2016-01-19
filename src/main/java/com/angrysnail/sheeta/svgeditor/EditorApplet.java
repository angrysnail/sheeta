package com.angrysnail.sheeta.svgeditor;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

/**
 * the class of the applet for the editor
 * 
 * @author Jordi SUC
 */
public class EditorApplet extends JApplet {

	/**
	 * the editor
	 */
	private Editor editor = null;

	@Override
	public void init() {

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {

					createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates the GUI
	 */
	private void createGUI() {

		// creating the editor object
		editor = new Editor();

		// intializing the editor
		editor.init(this, "", false, false, true, false, null);
	}

	@Override
	public void destroy() {

		if (editor != null) {

			editor.dispose();
		}

		super.destroy();
	}
}
