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
		if(f.getName().endsWith(".jpg")) {
			return true;
		}
		if(f.getName().endsWith(".jpeg")) {
			return true;
		}
		if(f.getName().endsWith(".tiff")) {
			return true;
		}
		if(f.getName().endsWith(".tif")) {
			return true;
		}
		if(f.getName().endsWith(".gif")) {
			return true;
		}
		if(f.getName().endsWith("png")) {
			return true;
		}
		if(f.getName().endsWith(".bmp")) {
			return true;
		}
		if(f.getName().endsWith(".WBMP")) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		
		return "supported image files";
	}

}