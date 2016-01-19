package com.angrysnail.sheeta.extension.jwidget.list.runtime;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.extension.jwidget.list.edition.ListEdition;
import com.angrysnail.sheeta.extension.jwidget.list.runtime.actions.ListLoadView;
import com.angrysnail.sheeta.extension.jwidget.list.runtime.actions.ListSendMeasure;
import com.angrysnail.sheeta.extension.jwidget.list.runtime.actions.ListSimpleSendCommand;
import com.angrysnail.sheeta.rtda.animaction.Action;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetRuntime;
import com.angrysnail.sheeta.rtda.jwidget.JWidgetToolkit;

/**
 * the class of the view browser runtime jwidget
 * 
 * @author ITRIS, Jordi SUC
 */
public class ListRuntime extends JWidgetRuntime {

	/**
	 * the jwidget id
	 */
	public static String jwidgetId = "ListWidget";

	/**
	 * the list
	 */
	private JList list;

	/**
	 * the listener to the list
	 */
	protected ListSelectionListener listener;

	/**
	 * the constructor of the class
	 * 
	 * @param picture
	 *            the svg picture
	 * @param element
	 *            the svg element defining the jwidget
	 */
	public ListRuntime(SVGPicture picture, Element element) {

		super(picture, element);
	}

	@Override
	public void initialize() {

		list = new JList();
		list.setModel(new DefaultListModel());
		DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectionModel(selectionModel);
		list.setCellRenderer(new DefaultListCellRenderer());

		component = new JScrollPane(list);

		// handling the look of the list
		JWidgetToolkit.handleLook(element, list);
		JWidgetToolkit.handleBackgroundAndBorderLook(element, list);
		JWidgetToolkit.handleBackgroundAndBorderLook(element, component);
		JWidgetToolkit.handleBackgroundAndBorderLook(element,
				((JScrollPane) component).getViewport());

		// creating the action listener
		listener = new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				for (Action action : getActions()) {

					action.performAction(e);
				}
			}
		};

		list.addListSelectionListener(listener);
	}

	@Override
	public Action createAction(Element actionElement) {

		Action action = null;

		if (actionElement != null) {

			String tagName = actionElement.getTagName();

			if (tagName.equals("rtda:simpleSendCommand")) {

				action = new ListSimpleSendCommand(picture, projectName,
						picture.getCanvas(), list, null, actionElement, this);

			} else if (tagName.equals("rtda:sendMeasure")) {

				action = new ListSendMeasure(picture, projectName,
						picture.getCanvas(), list, null, actionElement, this);

			} else if (tagName.equals("rtda:loadView")) {

				action = new ListLoadView(picture, projectName,
						picture.getCanvas(), list, null, actionElement, this);

			} else {

				action = super.createAction(actionElement);
			}
		}

		return action;
	}

	/**
	 * @return the jwidget edition class linked to this jwidget runtime class
	 */
	public static Class<?> getEditionClass() {

		return ListEdition.class;
	}

	@Override
	public void registerListeners() {

		if (listener != null) {

			list.removeListSelectionListener(listener);
			list.addListSelectionListener(listener);
		}
	}

	@Override
	public void unregisterListeners() {

		if (listener != null) {

			list.removeListSelectionListener(listener);
		}
	}

	@Override
	public void dispose() {

		unregisterListeners();
		super.dispose();
	}
}
