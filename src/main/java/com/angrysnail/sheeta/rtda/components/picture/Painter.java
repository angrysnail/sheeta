package com.angrysnail.sheeta.rtda.components.picture;

import java.awt.Graphics2D;

/**
 * the interface used to paint on the svg canvas
 * 
 * @author ITRIS, Jordi SUC
 */
public interface Painter {

	/**
	 * the method used to paint on the svg canvas
	 * 
	 * @param g
	 *            a graphics object
	 */
	public abstract void paint(Graphics2D g);
}
