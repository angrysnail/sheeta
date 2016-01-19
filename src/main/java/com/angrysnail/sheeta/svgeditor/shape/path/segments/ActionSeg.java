package com.angrysnail.sheeta.svgeditor.shape.path.segments;

import java.awt.geom.Point2D;

/**
 * the action segment class
 * 
 * @author Jordi SUC
 */
public abstract class ActionSeg {

	/**
	 * @return the end point of this segment
	 */
	public abstract Point2D getEndPoint();

	/**
	 * @return the last control point of this segment, if it exists
	 */
	public abstract Point2D getControlPoint();
}
