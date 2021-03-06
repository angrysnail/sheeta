package com.angrysnail.sheeta.library.geom;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * the class defining a polygon with points having double coordinates
 * 
 * @author ITRIS, Jordi SUC
 */
public class Polygon2D extends PolyShape2D {

	/**
	 * the constructor of the class
	 */
	public Polygon2D() {
	}

	/**
	 * the constructor of the class
	 * 
	 * @param coordinates
	 *            the array of the coordinates of the points defining the
	 *            polygon, the array size should be even
	 * @throws Exception
	 *             an exception raised if the array is null or empty and the
	 *             array size is not even
	 */
	public Polygon2D(double[] coordinates) throws Exception {

		super(coordinates);
	}

	/**
	 * the constructor of the class
	 * 
	 * @param polygon
	 *            the polygon to clone
	 */
	public Polygon2D(PolyShape2D polygon) {

		super(polygon);
	}

	/**
	 * the constructor of the class
	 * 
	 * @param thePoints
	 *            the array of the points defining the polygon, the array size
	 *            should be even
	 * @throws Exception
	 *             an exception raised if the array is null or empty and the
	 *             array size is not even
	 */
	public Polygon2D(Point2D[] thePoints) throws Exception {

		super(thePoints);
	}

	/**
	 * the constructor of the class
	 * 
	 * @param thePoints
	 *            the array of the points defining the polygon, the array size
	 *            should be even
	 * @throws Exception
	 *             an exception raised if the array is null or empty and the
	 *             array size is not even
	 */
	public Polygon2D(Point[] thePoints) throws Exception {

		super(thePoints);
	}

	@Override
	protected void fillPath() {

		super.fillPath();

		if (points.size() > 2) {

			// closing the path
			path.closePath();
		}
	}

	@Override
	public PolyShape2D cloneShape() {

		return new Polygon2D(this);
	}
}
