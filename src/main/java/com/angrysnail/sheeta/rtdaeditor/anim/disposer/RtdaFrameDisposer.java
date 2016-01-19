package com.angrysnail.sheeta.rtdaeditor.anim.disposer;

import java.io.File;

import com.angrysnail.sheeta.library.Toolkit;
import com.angrysnail.sheeta.rtda.animaction.ActionsLoader;
import com.angrysnail.sheeta.rtda.colorsblinkings.ColorsAndBlinkingsToolkit;
import com.angrysnail.sheeta.rtdaeditor.colorchooser.RtdaColorChooserModule;
import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.display.handle.HandlesDisposer;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;

/**
 * the class used to dispose rtda items when a frame is disposed
 * 
 * @author ITRIS, Jordi SUC
 */
public class RtdaFrameDisposer extends HandlesDisposer {

	@Override
	public void disposeHandle(SVGHandle handle) {

		// getting the project file associated to the given handle
		File projectFile = handle.getScrollPane().getSVGCanvas()
				.getProjectFile();

		if (projectFile != null && shouldDisposeProjectFiles(projectFile)) {

			// removing the loaded action classes
			ActionsLoader.removeClasses(ActionsLoader
					.getProjectName(projectFile));

			// removing the entry of this project file
			((RtdaColorChooserModule) Editor.getColorChooser())
					.getColorsAndBlinkingsToolkit().removeColorsAndBlinkings(
							ColorsAndBlinkingsToolkit
									.getProjectName(projectFile));

			// TODO
		}

		super.disposeHandle(handle);
	}

	/**
	 * returns whether to dispose all the resources associated with the given
	 * project
	 * 
	 * @param projectFile
	 *            the file of a project
	 * @return whether to dispose all the resources associated with the given
	 *         project
	 */
	public boolean shouldDisposeProjectFiles(File projectFile) {

		boolean shouldDispose = true;

		if (projectFile != null) {

			// getting the name of the project
			String projectName = Toolkit.getFileName(projectFile);
			String currentPrjName = "";
			File handleProjectFile = null;

			if (projectName != null && !projectName.equals("")) {

				// for each handle
				for (SVGHandle hnd : Editor.getEditor().getHandlesManager()
						.getHandles()) {

					// getting the project file of this handle
					handleProjectFile = hnd.getScrollPane().getSVGCanvas()
							.getProjectFile();

					if (handleProjectFile != null) {

						currentPrjName = Toolkit.getFileName(handleProjectFile);

						if (currentPrjName != null
								&& currentPrjName.equals(projectName)) {

							shouldDispose = false;
							break;
						}
					}
				}
			}
		}

		return shouldDispose;
	}
}
