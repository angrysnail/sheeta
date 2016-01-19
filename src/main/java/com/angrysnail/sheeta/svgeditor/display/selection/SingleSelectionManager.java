package com.angrysnail.sheeta.svgeditor.display.selection;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.svgeditor.display.canvas.CanvasPainter;
import com.angrysnail.sheeta.svgeditor.display.canvas.SVGCanvas;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;
import com.angrysnail.sheeta.svgeditor.display.undoredo.UndoRedoAction;
import com.angrysnail.sheeta.svgeditor.display.undoredo.UndoRedoActionList;
import com.angrysnail.sheeta.svgeditor.shape.AbstractShape;
import com.angrysnail.sheeta.svgeditor.shape.ShapeToolkit;

/**
 * the class handling selections, translations and actions when only a single
 * element is selected
 * 
 * @author ITRIS, Jordi SUC
 */
public class SingleSelectionManager extends SelectionManager {

	/**
	 * the canvas painter that is currently in use
	 */
	private CanvasPainter currentCanvasPainter = null;

	/**
	 * the constructor of the class
	 * 
	 * @param selectionObject
	 *            the selection object that uses this selection manager
	 */
	public SingleSelectionManager(Selection selectionObject) {

		this.selection = selectionObject;
	}

	@Override
	public int getNextSelectionLevel(Set<Element> elements) {

		// getting the selected element
		Element element = elements.iterator().next();

		// getting the current selection level
		int currentSelectionLevel = selection.getSelectionLevel();

		// getting the shape module corresponding to the element
		AbstractShape shape = ShapeToolkit.getShapeModule(element);

		if (shape != null) {

			// getting the max selection level for this module
			int maxSelectionLevel = shape.getLevelCount();

			// computing the new selection level
			return ((currentSelectionLevel + 1) % (maxSelectionLevel + 1));
		}

		return 0;
	}

	@Override
	public Set<SelectionItem> getSelectionItems(SVGHandle handle,
			Set<Element> elements, int level) {

		if (selection.isSelectionLocked()) {

			return getLockedSelectionItems(handle, elements);

		} else {

			// the selection items set that will be returned
			HashSet<SelectionItem> items = new HashSet<SelectionItem>();
			Element element = elements.iterator().next();

			// getting the shape module corresponding to this element
			AbstractShape shapeModule = ShapeToolkit.getShapeModule(element);

			if (shapeModule != null) {

				items.addAll(shapeModule.getSelectionItems(handle, elements,
						level));
			}

			return items;
		}
	}

	@Override
	public void doTranslateAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint) {

		// getting the element that will undergo the action
		Element element = elements.iterator().next();

		// getting the shape module corresponding to this action
		AbstractShape shape = ShapeToolkit.getShapeModule(element);

		if (shape != null) {

			// executing the translate action//

			// removing the previous canvas painter
			if (currentCanvasPainter != null) {

				selection.getSVGHandle().getScrollPane().getSVGCanvas()
						.removePaintListener(currentCanvasPainter, false);
			}

			currentCanvasPainter = shape.showTranslateAction(
					selection.getSVGHandle(), elements, firstPoint,
					currentPoint);

			if (currentCanvasPainter != null) {

				// repainting the canvas
				selection
						.getSVGHandle()
						.getScrollPane()
						.getSVGCanvas()
						.addLayerPaintListener(SVGCanvas.DRAW_LAYER,
								currentCanvasPainter, true);
			}
		}
	}

	@Override
	public void validateTranslateAction(Set<Element> elements,
			Point2D firstPoint, Point2D currentPoint) {

		// removing the current canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, true);
		}

		// getting the element that will undergo the action
		Element element = elements.iterator().next();

		// getting the shape module corresponding to this action
		AbstractShape shape = ShapeToolkit.getShapeModule(element);

		if (shape != null) {

			// validating the translate action
			UndoRedoAction undoRedoAction = shape.validateTranslateAction(
					selection.getSVGHandle(), elements, firstPoint,
					currentPoint);

			if (undoRedoAction != null) {

				// adding the undo/redo action
				UndoRedoActionList actionlist = new UndoRedoActionList(
						undoRedoAction.getName(), false);
				actionlist.add(undoRedoAction);
				selection.getSVGHandle().getUndoRedo()
						.addActionList(actionlist, true);
			}
		}
	}

	@Override
	public void doAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint, SelectionItem item) {

		// getting the element that will undergo the action
		Element element = elements.iterator().next();

		// getting the shape module corresponding to this action
		AbstractShape shape = ShapeToolkit.getShapeModule(element);

		// executing the action
		if (shape != null) {

			// removing the previous canvas painter
			if (currentCanvasPainter != null) {

				selection.getSVGHandle().getScrollPane().getSVGCanvas()
						.removePaintListener(currentCanvasPainter, false);
			}

			currentCanvasPainter = shape.showAction(selection.getSVGHandle(),
					selection.getSelectionLevel(), elements, item, firstPoint,
					currentPoint);

			if (currentCanvasPainter != null) {

				// repainting the canvas
				selection
						.getSVGHandle()
						.getScrollPane()
						.getSVGCanvas()
						.addLayerPaintListener(SVGCanvas.DRAW_LAYER,
								currentCanvasPainter, true);
			}
		}
	}

	@Override
	public void validateAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint, SelectionItem item) {

		// removing the current canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, true);
		}

		// getting the element that will undergo the action
		Element element = elements.iterator().next();

		// getting the shape module corresponding to this action
		AbstractShape shape = ShapeToolkit.getShapeModule(element);

		if (shape != null) {

			// validating the action
			UndoRedoAction undoRedoAction = shape.validateAction(
					selection.getSVGHandle(), selection.getSelectionLevel(),
					elements, item, firstPoint, currentPoint);

			if (undoRedoAction != null) {

				// adding the undo/redo action
				UndoRedoActionList actionlist = new UndoRedoActionList(
						undoRedoAction.getName(), false);
				actionlist.add(undoRedoAction);
				selection.getSVGHandle().getUndoRedo()
						.addActionList(actionlist, true);
			}
		}
	}
}
