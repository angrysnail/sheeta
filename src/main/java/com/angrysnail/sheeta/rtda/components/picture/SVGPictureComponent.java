package com.angrysnail.sheeta.rtda.components.picture;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;

import com.angrysnail.sheeta.rtda.components.viewbrowser.ViewBrowserPopupDialog;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;

/**
 * the component associated to the svg picture
 * 
 * @author Jordi SUC
 */
public class SVGPictureComponent extends JPanel {

	/**
	 * the svg picture
	 */
	private SVGPicture picture;

	/**
	 * the canvas into which the svg document will be displayed
	 */
	private SVGCanvas canvas;

	/**
	 * the desktop pane
	 */
	private JDesktopPane desktopPane;

	/**
	 * the index of the next component to be inserted in the layered pane
	 */
	private int innerComponentsIndex = 2;

	/**
	 * whether the canvas has been rendered
	 */
	private boolean isRendered = false;

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            a picture
	 */
	public SVGPictureComponent(SVGPicture picture) {

		this.picture = picture;

		// creating the canvas
		canvas = new SVGCanvas(picture);
		canvas.setVisible(false);

		// creating the desktop pane
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.white);
		desktopPane.setOpaque(false);
		desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		// adding the desktop pane to the component
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(desktopPane);
	}

	/**
	 * renders the svg image
	 */
	public void renderCanvas() {

		if (!picture.isDisposed) {

			if (!isRendered) {

				isRendered = true;

				// adding the listener to the rendering of the canvas
				canvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {

					@Override
					public void gvtRenderingCompleted(GVTTreeRendererEvent evt) {

						canvas.removeGVTTreeRendererListener(this);

						if (!picture.isDisposed) {

							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {

									// handling the jwidget elements
									for (JWidgetRuntime jwidgetRuntime : picture
											.getAnimActionsHandler()
											.getElementToJWidgetRuntime()
											.values()) {

										jwidgetRuntime.handleBounds();

										// adding the component of the jwidget
										// runtime to the
										// layered pane
										addInnerComponent(jwidgetRuntime
												.getComponent());
									}

									// initializing the animations and actions
									// before the
									// dataChanged method is called
									picture.getAnimActionsHandler()
											.initAnimActionsOnceCanvasVisible();

									// registering the animations and actions
									picture.getAnimActionsHandler()
											.addAnimationsActions();

									getCanvas().setVisible(true);

									if (picture.viewBrowser
											.equals(picture.mainDisplay
													.getMainViewBrowser())
											|| picture.mainDisplay
													.isTestVersion()) {

										picture.mainDisplay.refresh(picture);
									}

									packPopupWindow();
								}
							});
						}
					}
				});

				// setting the size for the canvas
				Dimension canvasSize = canvas.getCanvasSize(picture.doc);
				canvas.setSize(canvasSize);
				desktopPane.setSize(canvasSize);
				desktopPane.setPreferredSize(canvasSize);

				// adding the canvas
				desktopPane.add(canvas, new Integer(-1));

				// building the animations, the actions and the jwidgets
				picture.getAnimActionsHandler().buildAnimActionsJwidgets();

				// setting the document for the canvas
				canvas.setSVGDocument(picture.doc);

			} else {

				// adding the animations
				picture.getAnimActionsHandler().addAnimationsActions();

				// showing the canvas
				canvas.setVisible(true);
				validate();
				setVisible(true);

				if (picture.viewBrowser.equals(picture.mainDisplay
						.getMainViewBrowser())
						|| picture.mainDisplay.isTestVersion()) {

					picture.mainDisplay.refresh(picture);
				}

				packPopupWindow();
			}
		}
	}

	/**
	 * packs the popup window that is an ancestor of the canvas, if this
	 * ancestor is a popup window
	 */
	protected void packPopupWindow() {

		if (canvas != null) {

			Container container = canvas.getTopLevelAncestor();

			if (container != null
					&& container instanceof ViewBrowserPopupDialog) {

				ViewBrowserPopupDialog popupDialog = (ViewBrowserPopupDialog) container;

				popupDialog.showDialog();
			}
		}
	}

	/**
	 * @return the svg canvas
	 */
	public SVGCanvas getCanvas() {
		return canvas;
	}

	/**
	 * @return the desktop pane
	 */
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	/**
	 * @return whether the canvas has been rendered
	 */
	public boolean isRendered() {
		return isRendered;
	}

	/**
	 * inserts a new jcomponent in the layered pane
	 * 
	 * @param component
	 *            a component
	 */
	public void addInnerComponent(JComponent component) {

		if (component != null) {

			desktopPane.add(component, new Integer(innerComponentsIndex++));
		}
	}

	/**
	 * removes an inner component of the layered panel
	 * 
	 * @param component
	 *            a component
	 */
	public void removeInnerComponent(JComponent component) {

		if (component != null) {

			desktopPane.remove(component);
		}
	}

	/**
	 * disposes the component
	 */
	public void dispose() {

		if (desktopPane != null) {

			// cleaning this svg picture
			desktopPane.removeAll();
		}

		// disposing the canvas
		canvas.stopProcessing();
		canvas.clearPainters();

		try {
			canvas.dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
