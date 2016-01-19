/*
 * Created on 24 juin 2005
 */
package com.angrysnail.sheeta.rtdaeditor.dbeditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * @author ITRIS, Jordi SUC
 */
public class WidgetDataBaseCellEditor extends AbstractCellEditor
		implements
			TreeCellEditor {

	/**
	 * the tree associated with this cell editor
	 */
	private JTree widgetTree = null;

	/**
	 * the panel displaying the icon and the textField
	 */
	private JPanel labelAndTextField = new JPanel();

	/**
	 * the label that will contain the icon
	 */
	private JLabel iconLabel = new JLabel();

	/**
	 * the textfield that will be displayed for the editor
	 */
	private JTextField textField = new JTextField();

	/**
	 * the listener to the textfield
	 */
	private ActionListener textFieldListener;

	/**
	 * the caret listener to the textfield
	 */
	private CaretListener caretListener;

	/**
	 * the value for the editor
	 */
	private Object newValue;

	/**
	 * the constructor of the class
	 * 
	 * @param widgetTree
	 *            a JTree object
	 * @param renderer
	 *            a DefaultTreeCellRenderer object
	 */
	public WidgetDataBaseCellEditor(JTree widgetTree,
			WidgetDataBaseCellRenderer renderer) {

		this.widgetTree = widgetTree;

		// creating and adding the listener to the text field
		textFieldListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				// storing the new value
				newValue = textField.getText();
				fireEditingStopped();
			}
		};

		textField.addActionListener(textFieldListener);

		caretListener = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {

				// storing the new value
				newValue = textField.getText();
			}
		};

		textField.addCaretListener(caretListener);

		// building the panel containing the label and the icon
		iconLabel.setBackground(renderer.getBackgroundNonSelectionColor());
		labelAndTextField.setBackground(renderer
				.getBackgroundNonSelectionColor());
		labelAndTextField.setLayout(new BorderLayout(renderer.getIconTextGap(),
				0));
		labelAndTextField.add(iconLabel, BorderLayout.WEST);
		labelAndTextField.add(textField, BorderLayout.CENTER);
	}

	/**
	 * disposes this editor
	 */
	public void dispose() {

		textField.removeActionListener(textFieldListener);
		textField.removeCaretListener(caretListener);
	}

	/**
	 * @return the value for the editor
	 */
	@Override
	public Object getCellEditorValue() {

		return newValue;
	}

	@Override
	public boolean isCellEditable(EventObject event) {

		boolean isCellEditable = false;

		if (event instanceof MouseEvent) {

			MouseEvent evt = (MouseEvent) event;

			if (SwingUtilities.isLeftMouseButton(evt) && !evt.isPopupTrigger()) {

				// getting the currently selected tree path
				TreePath selectedTreePath = widgetTree.getSelectionPath();

				// getting the tree path corresponding to the hit point
				TreePath hitPath = widgetTree.getPathForLocation(evt.getX(),
						evt.getY());

				if (selectedTreePath != null && hitPath != null
						&& selectedTreePath.equals(hitPath)) {

					// if the path is already selected, it can be edited
					isCellEditable = true;
				}
			}
		}

		return isCellEditable;
	}

	/**
	 * @see javax.swing.tree.TreeCellEditor#getTreeCellEditorComponent(javax.swing.JTree,
	 *      java.lang.Object, boolean, boolean, boolean, int)
	 */
	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {

		if (value != null && value instanceof WidgetDataBaseEditorTreeNode) {

			// the node that will be edited
			WidgetDataBaseEditorTreeNode node = (WidgetDataBaseEditorTreeNode) value;

			// the icon associated with the node
			ImageIcon editingIcon = getIcon(node);

			// getting the id for the node
			String id = node.getId();

			// setting the new values for the component
			iconLabel.setIcon(editingIcon);
			textField.setText(id);
		}

		return labelAndTextField;
	}

	/**
	 * returning the icon corresponding to the given node
	 * 
	 * @param node
	 *            a node
	 * @return the icon corresponding to the given node
	 */
	protected ImageIcon getIcon(WidgetDataBaseEditorTreeNode node) {

		ImageIcon icon = null;

		if (node != null) {

			icon = ResourcesManager.getIcon(node.getIconName(), false);
		}

		return icon;
	}
}
