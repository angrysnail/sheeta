package com.angrysnail.sheeta.rtda;

import java.awt.Frame;

import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class of the toolkit of the main display object
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class MainDisplayToolkit {

	/**
	 * the method used when the user wants to quit the program
	 */
	public abstract void quitProgram();

	/**
	 * refreshes some elements
	 * 
	 * @param currentPicture
	 *            the current picture
	 */
	public abstract void refresh(SVGPicture currentPicture);

	/**
	 * @return the top level frame of this application
	 */
	public abstract Frame getTopLevelFrame();
}
