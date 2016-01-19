package com.angrysnail.sheeta.rtdaeditor.anim.components;

import java.awt.Component;

import javax.swing.JTable;

import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;
import com.angrysnail.sheeta.rtdaeditor.anim.widgets.Widget;

/**
 * the class of the renderer of the property table
 * 
 * @author ITRIS, Jordi SUC
 */
public class AnimationChildTableRenderer extends AnimationTableRenderer {

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
	 *      java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (value != null && value instanceof EditableItem) {

			return Widget.getWidget((EditableItem) value, null, false,
					isSelected);
		}

		return null;
	}
}
