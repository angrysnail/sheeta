package com.angrysnail.sheeta.view;

import java.awt.Frame;

import javax.swing.JFrame;

import com.angrysnail.sheeta.rtda.MainDisplayToolkit;
import com.angrysnail.sheeta.rtda.components.picture.SVGPicture;

/**
 * the class of the toolkit for the main display test
 * 
 * @author ITRIS, Jordi SUC
 */
public class ViewMainDisplayToolkit extends MainDisplayToolkit {

	/**
	 * the svg view object
	 */
	private View view;

	/**
	 * the constructor of the class
	 * 
	 * @param view
	 *            the svg view object
	 */
	public ViewMainDisplayToolkit(View view) {

		this.view = view;
	}

	@Override
	public void quitProgram() {

		view.getMainDisplay().getAnimationsHandler().dispose();
		System.exit(0);
	}

	@Override
	public void refresh(SVGPicture currentPicture) {

		if (view.getRegularFrame() != null
				&& view.getRegularFrame() instanceof JFrame
				&& currentPicture != null) {

			view.getRegularFrame().setPreferredSize(null);
			view.getRegularFrame().pack();
		}
	}

	@Override
	public Frame getTopLevelFrame() {

		return view.getRegularFrame();
	}
}
