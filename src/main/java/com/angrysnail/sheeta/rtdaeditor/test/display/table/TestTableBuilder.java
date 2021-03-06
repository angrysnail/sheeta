/*
 * Created on 3 févr. 2005
 */
package com.angrysnail.sheeta.rtdaeditor.test.display.table;

import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.angrysnail.sheeta.rtda.test.TestInteractor;
import com.angrysnail.sheeta.rtda.test.TestSimulationTagValues;

/**
 * @author ITRIS, Jordi SUC
 *
 *         the class allowing to build tables for the test simulation
 */
public class TestTableBuilder implements TestInteractor {

	/**
	 * the current table
	 */
	private TestTable table = null;

	/**
	 * the panel into which the scrollpane will be inserted
	 */
	private JPanel component = new JPanel();

	/**
	 * the constructor of the class
	 */
	public TestTableBuilder() {

		component.setLayout(new BoxLayout(component, BoxLayout.X_AXIS));
	}

	/**
	 * refreshes the interactor
	 */
	@Override
	public void refresh() {

		if (table != null) {

			table.repaint();
		}
	}

	/**
	 * disposes the current table
	 */
	public void disposeTable() {

		component.removeAll();

		if (table != null) {

			component.removeAll();
			table.removeAll();
			((TestTableModel) table.getModel()).clear();
			table = null;
		}
	}

	/**
	 * @return Returns the component.
	 */
	public JPanel getComponent() {
		return component;
	}

	/**
	 * creates a test table
	 * 
	 * @param testSimulationValues
	 *            the list of the simulation values objects
	 */
	public void refreshTestTable(
			LinkedList<TestSimulationTagValues> testSimulationValues) {

		component.removeAll();

		if (testSimulationValues != null) {

			// the model of the table
			TestTableModel tableModel = new TestTableModel(testSimulationValues);

			// creating the table
			table = new TestTable(tableModel, testSimulationValues);

			component.add(new JScrollPane(table));
			component.revalidate();
		}
	}

}
