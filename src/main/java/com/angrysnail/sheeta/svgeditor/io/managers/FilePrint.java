package com.angrysnail.sheeta.svgeditor.io.managers;

import javax.swing.JOptionPane;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.print.PrintTranscoder;
import org.w3c.dom.Document;

import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.display.handle.SVGHandle;
import com.angrysnail.sheeta.svgeditor.io.IOManager;
import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the class handling the printing of files
 * 
 * @author Jordi SUC
 */
public class FilePrint {

	/**
	 * the labels
	 */
	private String errorTitle = "", errorMessage = "";

	/**
	 * the constructor of the class
	 * 
	 * @param ioManager
	 *            the io manager
	 */
	public FilePrint(IOManager ioManager) {

		// getting the labels
		errorTitle = ResourcesManager.bundle.getString("FilePrintErrorTitle");
		errorMessage = ResourcesManager.bundle
				.getString("FilePrintErrorMessage");
	}

	/**
	 * prints the svg document graphic representation denoted by the provided
	 * handle
	 * 
	 * @param handle
	 *            the handle whose graphic representation should be saved
	 */
	public void print(SVGHandle handle) {

		// cloning the document of the handle
		Document clonedDocument = (Document) handle.getCanvas().getDocument()
				.cloneNode(true);

		// creating the transcoder input and output
		TranscoderInput input = new TranscoderInput(clonedDocument);
		TranscoderOutput output = new TranscoderOutput();

		// the print transcoder
		PrintTranscoder transcoder = new PrintTranscoder();

		transcoder.addTranscodingHint(PrintTranscoder.KEY_SHOW_PRINTER_DIALOG,
				new Boolean(true));

		try {
			transcoder.transcode(input, output);
			transcoder.print();
		} catch (Exception ex) {

			JOptionPane.showMessageDialog(Editor.getParent(), errorMessage,
					errorTitle, JOptionPane.WARNING_MESSAGE);
		}
	}
}
