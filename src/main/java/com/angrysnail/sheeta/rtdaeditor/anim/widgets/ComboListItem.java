/*
 * Created on 29 juil. 2004
 * 
 =============================================
 GNU LESSER GENERAL PUBLIC LICENSE Version 2.1
 =============================================
 GLIPS Graffiti Editor, a SVG Editor
 Copyright (C) 2003 Jordi SUC, Philippe Gil, SARL ITRIS

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 Contact : jordi.suc@itris.fr; philippe.gil@itris.fr

 =============================================
 */
package com.angrysnail.sheeta.rtdaeditor.anim.widgets;

/**
 * the class of the items in the combo boxes
 * 
 * @author ITRIS, Jordi SUC
 */
public class ComboListItem {

	/**
	 * the value of the item
	 */
	private Object value;

	/**
	 * the label of this item
	 */
	private String label;

	/**
	 * the constructor of the class
	 * 
	 * @param value
	 *            the value of the item
	 * @param label
	 *            the label of the item
	 */
	public ComboListItem(Object value, String label) {
		this.value = value;
		this.label = label;
	}

	/**
	 * @return the value of the item
	 */
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * sets the new values for the combo item
	 * 
	 * @param value
	 *            the value of the item
	 * @param label
	 *            the label of the item
	 */
	public void setValues(String value, String label) {

		this.value = value;
		this.label = label;
	}
}