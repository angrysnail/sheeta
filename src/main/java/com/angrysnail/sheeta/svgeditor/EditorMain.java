package com.angrysnail.sheeta.svgeditor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the enter point of the application
 * 
 * @author ITRIS, Jordi SUC
 */
public class EditorMain {

	/**
	 * the constructor of the class
	 *
	 * @param fileName
	 *            the name of a svg file
	 */
	public EditorMain(String fileName) {
		// creating the editor object
		final Editor editor = new Editor();
		// creating the parent frame of the editor
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Sheeta(Editor for SVG)");
		// handling the close case
		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				editor.exit();
			}
		});
		// setting the icon
		ImageIcon icon2 = ResourcesManager.getIcon("Editor", false);
		if (icon2 != null && icon2.getImage() != null) {
			mainFrame.setIconImage(icon2.getImage());
		}
		// intializing the editor
		editor.init(mainFrame, fileName, true, true, false, true, null);
	}

	/**
	 * the main method
	 * 
	 * @param args
	 *            the array of arguments
	 */
	public static void main(String[] args) {
		String fileName = "";
		if (args != null && args.length > 0) {
			fileName = args[0];
		}
		final String ffileName = fileName;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new EditorMain(ffileName);
			}
		});
	}
}
