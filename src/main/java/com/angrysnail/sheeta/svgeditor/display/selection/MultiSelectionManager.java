package com.angrysnail.sheeta.svgeditor.display.selection;

import java.awt.geom.Point2D;
import java.util.Set;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.display.canvas.CanvasPainter;
import com.angrysnail.sheeta.svgeditor.display.canvas.SVGCanvas;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;
import com.angrysnail.sheeta.svgeditor.display.undoredo.UndoRedoAction;
import com.angrysnail.sheeta.svgeditor.display.undoredo.UndoRedoActionList;
import com.angrysnail.sheeta.svgeditor.shape.ManyShape;

/**
 * the class handling selections, translations and actions when several elements
 * are selected
 * 
 * @author ITRIS, Jordi SUC
 */
public class MultiSelectionManager extends SelectionManager {

	/**
	 * the shape handler that deals with all the selections and actions that can
	 * be done on a multiple selection
	 */
	private ManyShape manyShape = new ManyShape(Editor.getEditor());

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
	public MultiSelectionManager(Selection selectionObject) {

		this.selection = selectionObject;
	}

	@Override
	public int getNextSelectionLevel(Set<Element> elements) {

		// getting the current selection level
		int currentSelectionLevel = selection.getSelectionLevel();

		// getting the max selection level for this module
		int maxSelectionLevel = manyShape.getLevelCount();

		// computing the new selection level
		return ((currentSelectionLevel + 1) % (maxSelectionLevel + 1));
	}

	@Override
	public Set<SelectionItem> getSelectionItems(SVGHandle handle,
			Set<Element> elements, int level) {

		if (selection.isSelectionLocked()) {

			return getLockedSelectionItems(handle, elements);

		} else {

			return manyShape.getSelectionItems(handle, elements, level);
		}
	}

	@Override
	public void doTranslateAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint) {

		// executing the translate action//

		// removing the previous canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, false);
		}

		currentCanvasPainter = manyShape.showTranslateAction(
				selection.getSVGHandle(), elements, firstPoint, currentPoint);

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

	@Override
	public void validateTranslateAction(Set<Element> elements,
			Point2D firstPoint, Point2D currentPoint) {

		// removing the current canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, true);
		}

		// validating the translate action
		UndoRedoAction undoRedoAction = manyShape.validateTranslateAction(
				selection.getSVGHandle(), elements, firstPoint, currentPoint);

		if (undoRedoAction != null) {

			// adding the undo/redo action
			UndoRedoActionList actionlist = new UndoRedoActionList(
					undoRedoAction.getName(), false);
			actionlist.add(undoRedoAction);
			selection.getSVGHandle().getUndoRedo()
					.addActionList(actionlist, true);
		}
	}

	@Override
	public void doAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint, SelectionItem item) {

		// executing the action//

		// removing the previous canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, false);
		}

		currentCanvasPainter = manyShape.showAction(selection.getSVGHandle(),
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

	@Override
	public void validateAction(Set<Element> elements, Point2D firstPoint,
			Point2D currentPoint, SelectionItem item) {

		// removing the current canvas painter
		if (currentCanvasPainter != null) {

			selection.getSVGHandle().getScrollPane().getSVGCanvas()
					.removePaintListener(currentCanvasPainter, true);
		}

		// validating the action
		UndoRedoAction undoRedoAction = manyShape.validateAction(
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
