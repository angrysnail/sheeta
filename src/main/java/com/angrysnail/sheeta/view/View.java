/*
 * Created on 26 janv. 2005
 */
package com.angrysnail.sheeta.view;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import com.angrysnail.sheeta.rtda.MainDisplay;
import com.angrysnail.sheeta.rtda.components.viewbrowser.SVGCanvasContainer;
import com.angrysnail.sheeta.svgeditor.EditorToolkit;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

/**
 * the main class for the view renderer
 * 
 * @author ITRIS, Jordi SUC
 */
public class View {

	/**
	 * the main display
	 */
	private MainDisplay mainDisplay;

	/**
	 * the canvas container
	 */
	private SVGCanvasContainer canvasContainer;

	/**
	 * the resource bundle
	 */
	private ResourceBundle bundle;

	/**
	 * the current directory of the viewer
	 */
	private File currentDirectory;

	/**
	 * whether the full screen mode is enabled or not
	 */
	private boolean isInFullScreenState = false;

	/**
	 * the regular frame
	 */
	private Frame regularFrame;

	/**
	 * the regular component
	 */
	private Container regularComponent;

	/**
	 * whether the full screen feature should be enabled
	 */
	private boolean enableFullscreen = true;

	/**
	 * whether the exit feature should be enabled
	 */
	private boolean enableExit = true;

	/**
	 * the constructor of the class
	 * 
	 * @param regularFrame
	 *            the regular frame
	 * @param regularComponent
	 *            the regular component
	 * @param enableFullscreen
	 *            whether the full screen feature should be enabled
	 * @param enableExit
	 *            whether to enable the exit command
	 * @param isDebugMode
	 *            whether we are in debug mode
	 */
	public View(Frame regularFrame, Container regularComponent,
			boolean enableFullscreen, boolean enableExit, boolean isDebugMode) {

		try {
			PlasticXPLookAndFeel laf = new PlasticXPLookAndFeel();
			UIManager.setLookAndFeel(laf);
			PlasticLookAndFeel.set3DEnabled(true);
		} catch (Exception e) {
		}

		this.regularFrame = regularFrame;
		this.regularComponent = regularFrame;
		this.enableFullscreen = enableFullscreen;
		this.enableExit = enableExit;

		// creating the resource bundle
		try {
			bundle = ResourceBundle
					.getBundle("com.angrysnail.sheeta.view.resource.properties.GLIPSView");
		} catch (Exception ex) {
			bundle = null;
			ex.printStackTrace();
		}

		// creating the main display toolkit
		ViewMainDisplayToolkit mainDisplayToolkit = new ViewMainDisplayToolkit(
				this);
		mainDisplay = new MainDisplay(mainDisplayToolkit, false, isDebugMode);
		mainDisplay.getAnimationsHandler().start();

		// creating the canvas container
		canvasContainer = mainDisplay.getMainViewBrowser().getCanvasContainer();

		mainDisplay.getAnimationsHandler().getInvalidityNotifier()
				.setInvalidMarkersEnabled(true);
	}

	/**
	 * @return the mainDisplay
	 */
	public MainDisplay getMainDisplay() {
		return mainDisplay;
	}

	/**
	 * @return Returns the canvasContainer.
	 */
	public SVGCanvasContainer getCanvasContainer() {
		return canvasContainer;
	}

	/**
	 * @return Returns the bundle.
	 */
	public ResourceBundle getBundle() {
		return bundle;
	}

	/**
	 * @return Returns the currentDirectory.
	 */
	public File getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * @param currentDirectory
	 *            The currentDirectory to set.
	 */
	public void setCurrentDirectory(File currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	/**
	 * sets whether the frame is in a full screen state
	 * 
	 * @param isInFullScreenState
	 *            whether the frame is in a full screen state
	 */
	public void setInFullScreenState(boolean isInFullScreenState) {
		this.isInFullScreenState = isInFullScreenState;
	}

	/**
	 * builds the menu bar
	 * 
	 * @return the menu bar
	 */
	public JMenuBar buildMenubar() {

		// creating the menu bar
		final JMenuBar menuBar = new JMenuBar();

		// getting the labels
		String fileMenuLabel = "", displayMenuLabel = "", openMenuItemLabel = "", exitMenuItemLabel = "", fullScreenEnterMenuItemLabel = "", fullScreenExitMenuItemLabel = "";

		if (bundle != null) {

			try {
				fileMenuLabel = bundle.getString("fileMenuLabel");
				displayMenuLabel = bundle.getString("diplayMenuLabel");
				openMenuItemLabel = bundle.getString("openMenuItemLabel");
				exitMenuItemLabel = bundle.getString("exitMenuItemLabel");
				fullScreenEnterMenuItemLabel = bundle
						.getString("fullScreenEnterMenuItemLabel");
				fullScreenExitMenuItemLabel = bundle
						.getString("fullScreenExitMenuItemLabel");
			} catch (Exception ex) {
			}
		}

		if (regularFrame != null) {

			// the file menu
			JMenu fileMenu = new JMenu(fileMenuLabel);
			menuBar.add(fileMenu);

			// the open menuItem
			JMenuItem openMenuItem = new JMenuItem(openMenuItemLabel);
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

			openMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {

					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(new FileFilter() {

						@Override
						public boolean accept(File file) {

							String name = file.getName();
							name = name.toLowerCase();

							if (file.isDirectory()
									|| name.endsWith(EditorToolkit.SVG_FILE_EXTENSION)) {

								return true;

							} else {

								return false;
							}
						}

						@Override
						public String getDescription() {

							return "fichiers .svg";
						}
					});

					if (getCurrentDirectory() != null) {

						chooser.setCurrentDirectory(getCurrentDirectory());
					}

					int returnVal = chooser.showOpenDialog(regularFrame);

					if (returnVal == JFileChooser.APPROVE_OPTION
							&& chooser.getSelectedFile() != null) {

						setCurrentDirectory(chooser.getCurrentDirectory());
						mainDisplay.getAnimationsHandler().stop();
						mainDisplay.getMainViewBrowser().disposeAll();
						mainDisplay
								.getMainViewBrowser()
								.getPictureLoader()
								.setCurrentPicture(
										chooser.getSelectedFile().toURI()
												.toASCIIString(), true);
						mainDisplay.getAnimationsHandler().start();
					}
				}
			});

			fileMenu.add(openMenuItem);

			if (enableExit) {

				// the exit menuItem
				JMenuItem exitMenuItem = new JMenuItem(exitMenuItemLabel);
				exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("ESCAPE"));

				exitMenuItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent evt) {

						System.exit(0);
					}
				});

				fileMenu.add(exitMenuItem);
			}

			if (enableFullscreen) {

				// the display menu
				JMenu displayMenu = new JMenu(displayMenuLabel);
				menuBar.add(displayMenu);

				// creating the menuItem used to display the canvas in a
				// full-screen manner
				final JMenuItem fullScreenMenuItem = new JMenuItem(
						fullScreenEnterMenuItemLabel);
				fullScreenMenuItem
						.setAccelerator(KeyStroke.getKeyStroke("F11"));

				final String ffullScreenEnterMenuItemLabel = fullScreenEnterMenuItemLabel, ffullScreenExitMenuItemLabel = fullScreenExitMenuItemLabel;

				final Runnable wideScreenModeRunnable = new Runnable() {

					@Override
					public void run() {

						regularFrame.setVisible(false);

						if (!isInFullScreenState) {

							fullScreenMenuItem
									.setText(ffullScreenExitMenuItemLabel);

							// hidding the frame and displaying a window in full
							// screen mode//
							if (regularComponent instanceof JFrame) {

								regularFrame.dispose();
								regularFrame.setUndecorated(true);
								((JFrame) regularComponent).getJMenuBar()
										.setVisible(false);
								regularFrame
										.setExtendedState(Frame.MAXIMIZED_BOTH);

							} else if (regularComponent instanceof JApplet) {

								/*
								 * ((JApplet)regularComponent).getContentPane().
								 * removeAll();
								 * ((JApplet)regularComponent).setJMenuBar
								 * (null); ((JApplet)regularComponent
								 * ).getContentPane().add(canvasContainer);
								 */
							}

							isInFullScreenState = true;

						} else {

							fullScreenMenuItem
									.setText(ffullScreenEnterMenuItemLabel);

							if (regularComponent instanceof JFrame) {

								regularFrame.dispose();
								regularFrame.setUndecorated(false);
								((JFrame) regularComponent).getJMenuBar()
										.setVisible(true);
								regularFrame.setExtendedState(Frame.NORMAL);

							} else if (regularComponent instanceof JApplet) {

								/*
								 * ((JApplet)regularComponent).getContentPane().
								 * removeAll();
								 * ((JApplet)regularComponent).setJMenuBar
								 * (menuBar); ((JApplet)regularComponent
								 * ).getContentPane().add(canvasContainer);
								 */
							}

							isInFullScreenState = false;
						}

						regularFrame.setVisible(true);
					}
				};

				final ActionListener fullScreenMenuItemListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent evt) {

						wideScreenModeRunnable.run();
					}
				};

				fullScreenMenuItem
						.addActionListener(fullScreenMenuItemListener);

				// the F11 action name
				String actionName = "f11Action";

				// registering the ok action
				Action f11Action = new AbstractAction(actionName) {

					@Override
					public void actionPerformed(ActionEvent e) {

						fullScreenMenuItemListener.actionPerformed(e);
					}
				};

				canvasContainer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
						.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0),
								actionName);
				canvasContainer.getActionMap().put(actionName, f11Action);

				displayMenu.add(fullScreenMenuItem);
			}
		}

		return menuBar;
	}

	/**
	 * @return the regular frame
	 */
	public Frame getRegularFrame() {

		return regularFrame;
	}
}
