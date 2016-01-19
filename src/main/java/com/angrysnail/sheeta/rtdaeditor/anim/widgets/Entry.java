package com.angrysnail.sheeta.rtdaeditor.anim.widgets;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;

/**
 * the class of the entry
 * 
 * @author ITRIS, Jordi SUC
 */
public class Entry extends Widget {

	/**
	 * the text field
	 */
	private final JTextField textField = new JTextField();

	/**
	 * the textfield listener
	 */
	private CaretListener textFieldListener = null;

	/**
	 * the focus listener of the text field
	 */
	private FocusAdapter textFieldFocusListener = null;

	/**
	 * the constructor of the class
	 * 
	 * @param isEditor
	 *            whether the widget should be used for editing or not
	 */
	protected Entry(boolean isEditor) {

		super(isEditor);
		buildWidget();
	}

	/**
	 * builds this widget
	 */
	protected void buildWidget() {

		textField.setOpaque(false);
		textField.setBorder(new EmptyBorder(0, 0, 0, 0));

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(textField);

		if (isEditor) {

			textField.setSelectionColor(new JTable().getSelectionBackground());

			// the text field listener
			textFieldListener = new CaretListener() {

				@Override
				public void caretUpdate(CaretEvent evt) {

					getItem().setValue(textField.getText());
				}
			};

			// the focus listener for the text field
			textFieldFocusListener = new FocusAdapter() {

				@Override
				public void focusLost(FocusEvent e) {

					validateRunnable = null;
					getItem().validateChanges();
					textField.removeFocusListener(textFieldFocusListener);
				}
			};
		}
	}

	@Override
	protected void setItem(EditableItem item, Runnable validateRunnable) {

		super.setItem(item, validateRunnable);

		if (isEditor) {

			textField.removeCaretListener(textFieldListener);
			textField.removeFocusListener(textFieldFocusListener);
		}

		textField.setText(item.getValue());

		if (isEditor) {

			textField.addCaretListener(textFieldListener);
			textField.addFocusListener(textFieldFocusListener);
		}
	}
}
