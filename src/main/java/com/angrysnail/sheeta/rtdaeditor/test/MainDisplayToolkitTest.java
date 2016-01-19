package com.angrysnail.sheeta.rtdaeditor.test;

import java.awt.Frame;

import com.angrysnail.sheeta.rtda.MainDisplayToolkit;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;
import com.angrysnail.sheeta.rtdaeditor.test.display.DialogTest;

/**
 * the class of the toolkit for the main display test
 * 
 * @author ITRIS, Jordi SUC
 */
public class MainDisplayToolkitTest extends MainDisplayToolkit {

	/**
	 * the dialog test
	 */
	private DialogTest dialogTest;

	/**
	 * the constructor of the class
	 * 
	 * @param dialogTest
	 *            the dialog for the dialog
	 */
	public MainDisplayToolkitTest(DialogTest dialogTest) {

		this.dialogTest = dialogTest;
	}

	@Override
	public void quitProgram() {

		dialogTest.refreshDialogState(false);
	}

	@Override
	public void refresh(SVGPicture currentPicture) {

		dialogTest.refreshSimulationValuesPanel();
	}

	@Override
	public Frame getTopLevelFrame() {

		return dialogTest.getFrame();
	}

	/**
	 * @return the dialogTest
	 */
	public DialogTest getDialogTest() {
		return dialogTest;
	}
}
