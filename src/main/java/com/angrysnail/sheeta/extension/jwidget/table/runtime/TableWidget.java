package com.angrysnail.sheeta.extension.jwidget.table.runtime;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;

/**
 * the class of the table widget
 * 
 * @author ITRIS, Jordi SUC
 */
public class TableWidget extends JComponent {

	/**
	 * the regex column separators
	 */
	public static String regexColumnNamesSeparator = "/[*][*]/";

	/**
	 * the jwidget runtime object
	 */
	private JWidgetRuntime jwidgetRuntime;

	/**
	 * the table model listener
	 */
	private TableModelListener tableModelListener;

	/**
	 * the table
	 */
	protected JTable table = new JTable();

	/**
	 * the table model
	 */
	private TableWidgetModel model;

	/**
	 * the constructor of the class
	 * 
	 * @param jwidgetRuntime
	 *            the jwidget runtime object
	 * @param jwidgetElement
	 *            the jwidget element defining the jwidget runtime object
	 */
	protected TableWidget(JWidgetRuntime jwidgetRuntime, Element jwidgetElement) {

		this.jwidgetRuntime = jwidgetRuntime;

		// creating the table model
		model = new TableWidgetModel(jwidgetElement);
		table.setModel(model);

		// setting the table presentation options
		boolean showHLines = false, showVLines = false;

		if (jwidgetElement.getAttribute("showHorizontalLines").equals(
				Boolean.toString(true))) {

			showHLines = true;
		}

		if (jwidgetElement.getAttribute("showVerticalLines").equals(
				Boolean.toString(true))) {

			showVLines = true;
		}

		table.setShowHorizontalLines(showHLines);
		table.setShowVerticalLines(showVLines);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setIntercellSpacing(new Dimension(1, 1));
		table.getTableHeader().setReorderingAllowed(false);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(new JScrollPane(table));

		// creating the listener to the table model
		tableModelListener = new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent evt) {

				String currentSourceId = evt.getFirstRow() + " "
						+ evt.getColumn();

				for (Action action : TableWidget.this.jwidgetRuntime
						.getActions()) {

					if (action.getSourceId().equals(currentSourceId)) {

						action.performAction(null);
					}
				}
			}
		};

		model.addTableModelListener(tableModelListener);
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @return the table model
	 */
	public TableModel getModel() {

		return table.getModel();
	}

	/**
	 * sets whether the cell corresponding to the given indices is editable or
	 * not
	 * 
	 * @param row
	 *            the row index
	 * @param col
	 *            the col index
	 * @param editable
	 *            whether the cell corresponding to the given indices is
	 *            editable or not
	 */
	public void setCellEditable(int row, int col, boolean editable) {

		model.setCellEditable(row, col, editable);
	}

	/**
	 * checks whether the given source if corresponds to an existing table cell
	 * 
	 * @param sourceId
	 *            the id of a table cell
	 * @return whether the given source if corresponds to an existing table cell
	 */
	public boolean cellExist(String sourceId) {

		// getting the cell coordinates in the table corresponding to the given
		// source
		int rowIndex = TableRuntime.getRow(sourceId);
		int colIndex = TableRuntime.getCol(sourceId);

		return rowIndex >= 0 && rowIndex < model.getRowCount() && colIndex >= 0
				&& colIndex < model.getColumnCount();
	}

	/**
	 * registers the listeners
	 */
	public void registerListeners() {

		model.removeTableModelListener(tableModelListener);
		model.addTableModelListener(tableModelListener);
	}

	/**
	 * unregisters the listeners
	 */
	public void unregisterListeners() {

		model.removeTableModelListener(tableModelListener);
	}

	/**
	 * disposes the component
	 */
	public void dispose() {

		unregisterListeners();
	}

	/**
	 * the class of the table model
	 * 
	 * @author ITRIS, Jordi SUC
	 */
	protected class TableWidgetModel extends AbstractTableModel {

		/**
		 * the array of the column names
		 */
		private String[] columnNames;

		/**
		 * the row count
		 */
		private int rowCount = 0;

		/**
		 * the data of the table
		 */
		private Object[][] data;

		/**
		 * the matrix specifying which cell in the table is editable or not
		 */
		private boolean[][] editables;

		/**
		 * the constructor of the class
		 * 
		 * @param jwidgetElement
		 *            the jwidget element
		 */
		public TableWidgetModel(Element jwidgetElement) {

			// getting the row count
			try {
				rowCount = Integer.parseInt(jwidgetElement
						.getAttribute("rowCount"));
			} catch (Exception ex) {
			}

			// getting the names of the columns
			String[] splitColNames = jwidgetElement.getAttribute("colNames")
					.split(regexColumnNamesSeparator);

			if (splitColNames != null && splitColNames.length != 0) {

				columnNames = new String[splitColNames.length];

				for (int i = 0; i < splitColNames.length; i++) {

					columnNames[i] = splitColNames[i];
				}

			} else {

				columnNames = new String[0];
			}

			data = new Object[rowCount][columnNames.length];
			editables = new boolean[rowCount][columnNames.length];

			for (int i = 0; i < rowCount; i++) {

				for (int j = 0; j < columnNames.length; j++) {

					editables[i][j] = false;
				}
			}
		}

		/**
		 * @return the number of columns
		 */
		@Override
		public int getColumnCount() {

			return columnNames.length;
		}

		/**
		 * @return the row count
		 */
		@Override
		public int getRowCount() {

			return rowCount;
		}

		/**
		 * returns the value corresponding to the given row and column indices
		 * 
		 * @param row
		 *            the row index
		 * @param col
		 *            the column index
		 * @return the value corresponding to the given row and column indices
		 */
		@Override
		public Object getValueAt(int row, int col) {

			return data[row][col];
		}

		@Override
		public void setValueAt(Object value, int row, int col) {

			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

		@Override
		public String getColumnName(int col) {

			return columnNames[col];
		}

		/**
		 * sets whether the cell corresponding to the given indices is editable
		 * or not
		 * 
		 * @param row
		 *            the row index
		 * @param col
		 *            the col index
		 * @param editable
		 *            whether the cell corresponding to the given indices is
		 *            editable or not
		 */
		public void setCellEditable(int row, int col, boolean editable) {

			editables[row][col] = editable;
		}

		@Override
		public boolean isCellEditable(int row, int col) {

			return editables[row][col];
		}
	}
}