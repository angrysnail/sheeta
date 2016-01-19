package com.angrysnail.sheeta.rtdaeditor.dbchooser;

import javax.swing.tree.DefaultTreeModel;

/**
 * the class of the model of the data base tree node
 * 
 * @author ITRIS, Jordi SUC
 */
public class DataBaseNodeModel extends DefaultTreeModel {

	/**
	 * the constructor of the class
	 * 
	 * @param rootTreeNode
	 *            the root tree node
	 */
	public DataBaseNodeModel(DataBaseTreeNode rootTreeNode) {

		super(rootTreeNode);
	}
}
