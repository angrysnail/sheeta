package com.angrysnail.sheeta.rtdaeditor.anim.widgets;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;

/**
 * the class of the label
 * 
 * @author ITRIS, Jordi SUC
 */
public class Label extends Widget {

	/**
	 * the label
	 */
	private JLabel label = null;

	/**
	 * the constructor of the class
	 * 
	 * @param isEditor
	 *            whether the widget should be used for editing or not
	 */
	protected Label(boolean isEditor) {

		super(isEditor);
		buildWidget();
	}

	/**
	 * builds this widget
	 */
	protected void buildWidget() {

		label = new JLabel();
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setOpaque(false);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(label);
	}

	@Override
	protected void setItem(EditableItem item, Runnable validateRunnable) {

		super.setItem(item, validateRunnable);
		label.setText(item.getValue());
	}
}
