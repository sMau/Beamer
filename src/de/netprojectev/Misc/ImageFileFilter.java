package de.netprojectev.Misc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class is an imagefile filter that only accept the standard java supported image formats
 * @author samu
 *
 */
public class ImageFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		if(f.isDirectory()) {
		    return true;
		}
		String fileName = f.getName().toLowerCase();;
		if(fileName.endsWith(".jpg")) {
			return true;
		}
		if(fileName.endsWith(".jpeg")) {
			return true;
		}
		if(fileName.endsWith(".tiff")) {
			return true;
		}
		if(fileName.endsWith(".tif")) {
			return true;
		}
		if(fileName.endsWith(".gif")) {
			return true;
		}
		if(fileName.endsWith("png")) {
			return true;
		}
		if(fileName.endsWith(".bmp")) {
			return true;
		}
		if(fileName.endsWith(".wbmp")) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		
		return "supported image files";
	}

}