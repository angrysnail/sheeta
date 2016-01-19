package com.angrysnail.sheeta.svgeditor.io.managers.export.image;

import java.awt.image.BufferedImage;

/**
 * the class of the listener to the image creator
 * 
 * @author ITRIS, Jordi SUC
 */
public abstract class SVGDocumentImageCreatorListener {

	/**
	 * notifies that the image of the svg file has been created
	 * 
	 * @param image
	 *            an image
	 */
	public abstract void imageCreated(BufferedImage image);
}
