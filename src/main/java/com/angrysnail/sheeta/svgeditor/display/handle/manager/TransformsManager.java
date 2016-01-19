package com.angrysnail.sheeta.svgeditor.display.handle.manager;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.display.canvas.rulers.RulersParametersManager;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;

/**
 * the class provided methods to handle conversion of geometry shape on the
 * canvas
 * 
 * @author Jordi SUC
 */
public class TransformsManager {

	/**
	 * the related svg handle
	 */
	private SVGHandle handle;

	/**
	 * the constructor of the class
	 * 
	 * @param handle
	 *            a svg handle
	 */
	public TransformsManager(SVGHandle handle) {

		this.handle = handle;
	}

	/**
	 * scales the given shape
	 * 
	 * @param shape
	 *            the shape to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @return the scaled shape
	 */
	public Shape getScaledShape(Shape shape, boolean toBaseScale) {

		Shape returnShape = shape;

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			returnShape = af.createTransformedShape(shape);

		} else {

			// applying the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			returnShape = af.createTransformedShape(shape);
		}

		return returnShape;
	}

	/**
	 * scales the given shape, concatenating the provided transform to the
	 * canvas transform
	 * 
	 * @param shape
	 *            the shape to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @param transform
	 *            the transform that will be concatenated to the canvas
	 *            transform
	 * @return the scaled shape
	 */
	public Shape getScaledShape(Shape shape, boolean toBaseScale,
			AffineTransform transform) {

		Shape returnShape = shape;

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			if (transform != null) {

				af.preConcatenate(transform);
			}

			returnShape = af.createTransformedShape(shape);

		} else {

			// applying the transforms
			AffineTransform af = new AffineTransform();

			if (transform != null) {

				af.preConcatenate(transform);
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			returnShape = af.createTransformedShape(shape);
		}

		return returnShape;
	}

	/**
	 * scales the given rectangle
	 * 
	 * @param rectangle
	 *            the rectangle to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @param transform
	 *            the transform that will be concatenated to the canvas
	 *            transform
	 * @return the scaled rectangle
	 */
	public Rectangle2D getScaledRectangle(Rectangle2D rectangle,
			boolean toBaseScale, AffineTransform transform) {

		Rectangle2D rect = new Rectangle2D.Double(rectangle.getX(),
				rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			if (transform != null) {

				af.preConcatenate(transform);
			}

			Rectangle2D rect2 = af.createTransformedShape(rect).getBounds2D();
			rect = new Rectangle2D.Double(rect2.getX(), rect2.getY(),
					rect2.getWidth(), rect2.getHeight());

		} else {

			// applying the transforms
			AffineTransform af = new AffineTransform();

			if (transform != null) {

				af.preConcatenate(transform);
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			Rectangle2D rect2 = af.createTransformedShape(rect).getBounds2D();
			rect = new Rectangle2D.Double(rect2.getX(), rect2.getY(),
					rect2.getWidth(), rect2.getHeight());
		}

		return rect;
	}

	/**
	 * scales the given rectangle
	 * 
	 * @param rectangle
	 *            the rectangle to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @return the scaled rectangle
	 */
	public Rectangle2D getScaledRectangle(Rectangle2D rectangle,
			boolean toBaseScale) {

		Rectangle2D rect = new Rectangle2D.Double(rectangle.getX(),
				rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			Rectangle2D rect2 = af.createTransformedShape(rect).getBounds2D();
			rect = new Rectangle2D.Double(rect2.getX(), rect2.getY(),
					rect2.getWidth(), rect2.getHeight());

		} else {

			// applying the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			Rectangle2D rect2 = af.createTransformedShape(rect).getBounds2D();
			rect = new Rectangle2D.Double(rect2.getX(), rect2.getY(),
					rect2.getWidth(), rect2.getHeight());
		}

		return rect;
	}

	/**
	 * scales the given point
	 * 
	 * @param point
	 *            the point to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @return the scaled point
	 */
	public Point2D getScaledPoint(Point2D point, boolean toBaseScale) {

		Point2D point2D = new Point2D.Double(point.getX(), point.getY());

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			point2D = af.transform(point, new Point2D.Double());

		} else {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			point2D = af.transform(point, new Point2D.Double());
		}

		return point2D;
	}

	/**
	 * scales the given point with the canvas transform concatenated to the
	 * provided transform
	 * 
	 * @param point
	 *            the point to scale
	 * @param toBaseScale
	 *            true to scale it to 100% false to scale it at the current
	 *            canvas scale
	 * @param transform
	 *            the transform that will be concatenated to the canvas
	 *            transform
	 * @return the scaled point
	 */
	public Point2D getScaledPoint(Point2D point, boolean toBaseScale,
			AffineTransform transform) {

		Point2D point2D = new Point2D.Double(point.getX(), point.getY());

		if (toBaseScale) {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform()
						.createInverse());
			} catch (Exception ex) {
			}

			if (transform != null) {

				af.preConcatenate(transform);
			}

			point2D = af.transform(point, new Point2D.Double());

		} else {

			// applying the inverse of the transforms
			AffineTransform af = new AffineTransform();

			if (transform != null) {

				af.preConcatenate(transform);
			}

			try {
				af.preConcatenate(handle.getCanvas().getViewingTransform());
			} catch (Exception ex) {
			}

			try {
				af.preConcatenate(handle.getCanvas().getRenderingTransform());
			} catch (Exception ex) {
			}

			point2D = af.transform(point, new Point2D.Double());
		}

		return point2D;
	}

	/**
	 * the method used to get the point corresponding to the given point when
	 * aligned with the rulers
	 * 
	 * @param point
	 *            the point
	 * @param handleScale
	 *            whether the point should be scaled to the 1.0 factor
	 * @return the point correponding to the given point when aligned with the
	 *         rulers
	 */
	public Point2D getAlignedWithRulersPoint(Point2D point, boolean handleScale) {// TODO

		Point2D newPoint = point;

		if (point != null) {

			// getting the rulers manager
			RulersParametersManager manager = Editor.getEditor()
					.getHandlesManager().getRulersParametersHandler();
			boolean alignWithRulers = manager.alignWithRulers();

			if (alignWithRulers) {

				Point2D rulersRanges = new Point2D.Double(handle
						.getScrollPane().getHorizontalRulerRange(), handle
						.getScrollPane().getVerticalRulerRange());
				newPoint = handle.getTransformsManager().getScaledPoint(point,
						true);

				if (newPoint != null) {

					double x = Math
							.round(newPoint.getX() / rulersRanges.getX())
							* rulersRanges.getX();
					double y = Math
							.round(newPoint.getY() / rulersRanges.getY())
							* rulersRanges.getY();
					newPoint = new Point2D.Double(x, y);
				}

				if (!handleScale) {

					newPoint = handle.getTransformsManager().getScaledPoint(
							newPoint, false);
				}

			} else if (handleScale) {

				newPoint = handle.getTransformsManager().getScaledPoint(point,
						true);
			}
		}

		return newPoint;
	}
}
