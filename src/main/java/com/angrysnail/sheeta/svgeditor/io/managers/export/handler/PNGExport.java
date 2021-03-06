package com.angrysnail.sheeta.svgeditor.io.managers.export.handler;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JComponent;
import javax.swing.JDialog;

import org.w3c.dom.Document;

import com.angrysnail.sheeta.svgeditor.Editor;
import com.angrysnail.sheeta.svgeditor.io.managers.export.FileExport;
import com.angrysnail.sheeta.svgeditor.io.managers.export.handler.dialog.ExportDialog;
import com.angrysnail.sheeta.svgeditor.io.managers.export.handler.dialog.PNGExportDialog;
import com.angrysnail.sheeta.svgeditor.io.managers.export.monitor.ExportMonitor;
import com.keypoint.PngEncoder;

/**
 * the class used to export images in a jpg format
 * 
 * @author ITRIS, Jordi SUC
 */
public class PNGExport extends Export {

	/**
	 * the compression level
	 */
	private int compressionLevel = 9;

	/**
	 * whether to encode the alpha channel or not
	 */
	private boolean encodeAlpha = true;

	/**
	 * the constructor of the class
	 * 
	 * @param fileExport
	 *            the object manager the export
	 */
	protected PNGExport(FileExport fileExport) {

		super(fileExport);

		// creating the dialog
		if (Editor.getParent() instanceof Frame) {

			exportDialog = new PNGExportDialog((Frame) Editor.getParent());

		} else {

			exportDialog = new PNGExportDialog((JDialog) Editor.getParent());
		}
	}

	@Override
	public void export(JComponent relativeComponent, Document document,
			File destFile) {

		monitor = new ExportMonitor(Editor.getParent(), 0, 100,
				FileExport.prefixLabels[1]);
		monitor.setRelativeComponent(relativeComponent);
		PNGExportDialog pngExportDialog = (PNGExportDialog) exportDialog;

		// showing the dialog used to select the values of the parameters for
		// the export
		int res = exportDialog.showExportDialog(document);

		if (res == ExportDialog.OK_ACTION) {

			// getting the parameters
			width = pngExportDialog.getExportSize().getX();
			height = pngExportDialog.getExportSize().getY();
			compressionLevel = pngExportDialog.getCompressionLevel();
			encodeAlpha = pngExportDialog.encodeAlpha();

			// creating the image
			createImage(document, destFile, encodeAlpha);
		}
	}

	@Override
	protected void writeImage(final BufferedImage image, final File destFile) {

		Thread thread = new Thread() {

			@Override
			public void run() {

				try {
					PngEncoder encoder = new PngEncoder(image, encodeAlpha,
							PngEncoder.FILTER_NONE, compressionLevel);
					monitor.setProgress(55);

					// encoding the image
					byte[] encodedBytes = encoder.pngEncode();
					monitor.setProgress(90);

					if (monitor.isCancelled()) {

						monitor.stop();
						return;
					}

					// getting the output stream handler
					FileChannel outChannel = new FileOutputStream(destFile)
							.getChannel();

					// creating the buffer
					ByteBuffer buffer = ByteBuffer
							.allocate(encodedBytes.length);
					buffer.put(encodedBytes);
					buffer.clear();

					// writing the file
					outChannel.write(buffer);
					outChannel.close();

					// stopping the monitor
					monitor.stop();

				} catch (Exception ex) {

					ex.printStackTrace();
					handleExportFailure();
				}
			}
		};

		thread.start();
	}
}
