package com.angrysnail.sheeta.rtdaeditor.anim.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.angrysnail.sheeta.library.Toolkit;
import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;

/**
 * the class of the event chooser
 * 
 * @author ITRIS, Jordi SUC
 */
public class SymbolChooser extends Widget {

	/**
	 * the id attribute
	 */
	private static String idAttribute = "id";

	/**
	 * the combo
	 */
	private JComboBox combo = new JComboBox();

	/**
	 * the combo listener
	 */
	private ActionListener comboListener = null;

	/**
	 * the constructor of the class
	 * 
	 * @param isEditor
	 *            whether the widget should be used for editing or not
	 */
	protected SymbolChooser(boolean isEditor) {

		super(isEditor);
		buildWidget();
	}

	/**
	 * builds the widget
	 */
	protected void buildWidget() {

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(combo);

		if (isEditor) {

			// adding the listener to the combo
			comboListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {

					if (getItem() != null && combo.getSelectedItem() != null) {

						ComboListItem comboItem = (ComboListItem) combo
								.getSelectedItem();

						if (comboItem != null) {

							getItem().setValue(
									Toolkit.toURLValue(comboItem.getValue()
											.toString()));
						}
					}

					// validating
					if (validateRunnable != null) {

						validateRunnable.run();
					}
				}
			};
		}
	}

	@Override
	protected void setItem(EditableItem item, Runnable validateRunnable) {

		super.setItem(item, validateRunnable);

		// getting the list of the symbols
		LinkedList<String> symbols = getSymbols(item.getSVGElement());

		if (isEditor) {

			combo.removeActionListener(comboListener);

			// clearing the combo box
			combo.removeAllItems();

			// getting the current value
			String currentValue = item.getValue();

			if (currentValue == null) {

				currentValue = "";

			} else {

				currentValue = Toolkit.toUnURLValue(currentValue);
			}

			// getting the value that should be selected
			String valueToSelect = currentValue;

			if (valueToSelect.equals("")) {

				valueToSelect = item.getDefaultValue();
			}

			// if no default value exists the empty item is used
			ComboListItem emptyItem = new ComboListItem("", "");
			combo.addItem(emptyItem);

			// filling the combo
			ComboListItem comboItem = null, selectedComboItem = emptyItem;

			for (String symbol : symbols) {

				comboItem = new ComboListItem(symbol, symbol);

				if (currentValue.equals(symbol)) {

					selectedComboItem = comboItem;
				}

				combo.addItem(comboItem);
			}

			// setting the new selected item
			combo.setSelectedItem(selectedComboItem);
			combo.addActionListener(comboListener);

		} else {

			// showing the value of the item in the combo
			combo.removeAllItems();
			String value = Toolkit.toUnURLValue(item.getValue());

			if (!symbols.contains(value)) {

				value = "";
			}

			combo.addItem(new ComboListItem(value, value));
		}
	}

	/**
	 * returns the list of the symbols that can be found under the given node
	 * 
	 * @param parentNode
	 *            a node
	 * @return the list of the symbols that can be found under the given node
	 */
	protected LinkedList<String> getSymbols(Node parentNode) {

		LinkedList<String> symbols = new LinkedList<String>();

		if (parentNode != null && parentNode.getNodeName().equals("g")) {

			String id = "";

			for (Node cur = parentNode.getFirstChild(); cur != null; cur = cur
					.getNextSibling()) {

				if (cur instanceof Element
						&& !cur.getNodeName().startsWith("rtda:")) {

					id = ((Element) cur).getAttribute(idAttribute);

					if (id != null && !id.equals("")) {

						symbols.add(id);
					}
				}
			}
		}

		return symbols;
	}

}
