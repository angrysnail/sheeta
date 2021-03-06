/*
 * Created on 24 mars 2004
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
package com.angrysnail.sheeta.svgeditor.io.managers.dialog;

import java.io.File;

import com.angrysnail.sheeta.svgeditor.io.managers.export.FileExport;
import com.angrysnail.sheeta.svgeditor.resources.ResourcesManager;

/**
 * the class for filtering files in a JFileChooser
 * 
 * @author ITRIS, Jordi SUC
 */
public class ExportFileFilter extends AbstractFileFilter {

	/**
	 * the extension
	 */
	private String extension = "";

	/**
	 * the description of the file filter
	 */
	private String description = "";

	/**
	 * the constructor of the class
	 * 
	 * @param type
	 *            the type of the file of the export
	 */
	public ExportFileFilter(int type) {

		extension = FileExport.extensions[type];

		description = ResourcesManager.bundle
				.getString(FileExport.prefixLabels[type] + "MessageFileFilter");
	}

	@Override
	public boolean accept(File f) {

		String name = f.getName().toLowerCase();

		if (f.isDirectory() || name.endsWith(extension)) {

			return true;

		} else {

			return false;
		}
	}

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public boolean acceptFile(File f) {

		String name = f.getName().toLowerCase();

		if (name.endsWith(extension)) {

			return true;
		}

		return false;
	}

	@Override
	public String getDefaultExtension() {

		return extension;
	}
}
