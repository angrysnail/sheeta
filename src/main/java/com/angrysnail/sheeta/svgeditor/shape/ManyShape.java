package com.angrysnail.sheeta.svgeditor.shape;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;

import com.angrysnail.sheeta.svgeditor.Editor;

/**
 * the class used to handle all the selections and actions that can be done on a
 * multiple selection
 * 
 * @author ITRIS, Jordi SUC
 */
public class ManyShape extends MultiAbstractShape {

	/**
	 * the constructor of the class
	 * 
	 * @param editor
	 *            the editor
	 */
	public ManyShape(Editor editor) {

		super(editor);
		shapeModuleId = "ManyShape";
	}

	@Override
	public Set<Element> getElements(Set<Element> elements) {

		return new HashSet<Element>(elements);
	}
}
