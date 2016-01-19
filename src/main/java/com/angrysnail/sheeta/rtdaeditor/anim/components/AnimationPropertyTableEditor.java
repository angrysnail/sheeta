package com.angrysnail.sheeta.rtdaeditor.anim.components;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;

import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;
import com.angrysnail.sheeta.rtdaeditor.anim.widgets.Widget;

/**
 * the class of the editor of the properties table
 * 
 * @author ITRIS, Jordi SUC
 */
public class AnimationPropertyTableEditor extends AnimationTableEditor {

	/**
	 * the current item
	 */
	protected EditableItem currentItem = null;

	/**
	 * the selected row when the editing action began
	 */
	protected int lastSelectedRow = -1;

	/**
	 * the constructor of the class
	 * 
	 * @param animationTable
	 *            the table
	 */
	protected AnimationPropertyTableEditor(AnimationTable animationTable) {

		super(animationTable);
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable,
	 *      java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {

		((AnimationTable) table).cancelOtherEditings();
		lastSelectedRow = row;

		if (value != null && value instanceof EditableItem) {

			final EditableItem item = (EditableItem) value;
			currentItem = item;

			if (column == 1) {

				Runnable validateRunnable = new Runnable() {

					@Override
					public void run() {

						fireEditingStopped();
					}
				};

				return Widget.getWidget(item, validateRunnable, true, false);
			}
		}

		return null;
	}

	@Override
	protected void fireEditingStopped() {

		super.fireEditingStopped();
		animationTable.getSelectionModel().setSelectionInterval(
				lastSelectedRow, lastSelectedRow);
	}

	/**
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {

		Object returnValue = currentItem;
		currentItem = null;

		return returnValue;
	}

	@Override
	public boolean isCellEditable(EventObject evt) {

		return true;
	}
}
