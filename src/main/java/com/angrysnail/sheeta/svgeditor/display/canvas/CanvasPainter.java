package com.angrysnail.sheeta.svgeditor.display.canvas;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

/**
 * The interface of the class used to paint on the canvas
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class CanvasPainter {

	/**
	 * the action that has to be done
	 * 
	 * @param g
	 *            the graphics object
	 */
	public abstract void paintToBeDone(Graphics2D g);

	/**
	 * @return the set of the clip zones that should be painted
	 */
	public Set<Rectangle2D> getClip() {

		return null;
	}
}
