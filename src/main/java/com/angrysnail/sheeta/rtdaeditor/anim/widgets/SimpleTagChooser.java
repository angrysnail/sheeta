package com.angrysnail.sheeta.rtdaeditor.anim.widgets;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.angrysnail.sheeta.rtdaeditor.anim.EditableItem;
import com.angrysnail.sheeta.rtdaeditor.dbchooser.DataBaseNodeChooser;
import com.angrysnail.sheeta.rtdaeditor.dbchooser.DataBaseNodeFilter;
import com.angrysnail.sheeta.rtdaeditor.dbchooser.DataBaseNodeInformation;
import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the class of the simple tag chooser
 * 
 * @author ITRIS, Jordi SUC
 */
public class SimpleTagChooser extends Widget {

	/**
	 * the label
	 */
	private final JLabel jlabel = new JLabel();

	/**
	 * the button used to launch the dialog
	 */
	private final JButton moreButton = new JButton();

	/**
	 * the labels
	 */
	private String noneLabel = "";

	/**
	 * the constructor of the class
	 * 
	 * @param isEditor
	 *            whether the widget should be used for editing or not
	 */
	protected SimpleTagChooser(boolean isEditor) {

		super(isEditor);
		buildWidget();
	}

	/**
	 * builds this widget
	 */
	protected void buildWidget() {

		// getting the labels
		String tagChooserLabel = "";
		ResourceBundle bundle = ResourcesManager.bundle;

		try {
			tagChooserLabel = bundle.getString("rtdaanim_tagchooserbutton");
			noneLabel = "<" + bundle.getString("rtdaanim_none") + ">";
		} catch (Exception ex) {
		}

		moreButton.setText(tagChooserLabel);
		moreButton.setMargin(new Insets(1, 1, 1, 1));
		jlabel.setOpaque(false);

		setLayout(new BorderLayout(1, 0));
		add(jlabel, BorderLayout.CENTER);
		add(moreButton, BorderLayout.EAST);

		if (isEditor) {

			// adding the listener to the button
			moreButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {

					// creating the filter for choosing a tag
					DataBaseNodeFilter filter = new DataBaseNodeFilter(
							getItem().getValue(), 0, getItem().getTagType(),
							false, false, null);

					// getting the information object about the selected tag
					DataBaseNodeChooser nodeChooser = DataBaseNodeChooser
							.getDataBaseNodeChooser(Editor.getParent(),
									getItem().getDocument(), filter, false,
									false);
					nodeChooser.showDialog(((JComponent) evt.getSource()));
					nodeChooser.disposeDialog();
					DataBaseNodeInformation info = nodeChooser.getInfo();

					if (info != null) {

						String newValue = info.getXmlPath();

						if (newValue == null) {

							newValue = "";
						}

						getItem().setValue(newValue);
					}

					if (validateRunnable != null) {

						validateRunnable.run();
					}
				}
			});
		}
	}

	@Override
	protected void setItem(EditableItem item, Runnable validateRunnable) {

		super.setItem(item, validateRunnable);

		String value = item.getValue();

		if (value.equals("")) {

			value = noneLabel;
		}

		jlabel.setText(value);
	}
}
