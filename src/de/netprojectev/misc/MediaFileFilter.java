package de.netprojectev.misc;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * This class is an imagefile filter that only accept the standard java supported image formats
 * @author samu
 *
 */
public class MediaFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		
		if(f.isDirectory()) {
		    return true;
		}
		
		String fileName = f.getName().toLowerCase();
		
		if(isImageFile(fileName)) {
			return true;
		}
		
		if(isVideoFile(fileName)) {
			return true;
		}

		return false;
	}
	
	//TODO check the vlc support list

	public static boolean isVideoFile(String fileName) {
		
		if(fileName.endsWith(".mp4")) {
			return true;
		}
		if(fileName.endsWith(".mkv")) {
			return true;
		}
		if(fileName.endsWith(".mpg")) {
			return true;
		}
		if(fileName.endsWith(".mpeg")) {
			return true;
		}
		if(fileName.endsWith(".3gp")) {
			return true;
		}
		if(fileName.endsWith(".3gp2")) {
			return true;
		}
		if(fileName.endsWith(".3gpp")) {
			return true;
		}
		if(fileName.endsWith(".avi")) {
			return true;
		}
		if(fileName.endsWith(".divx")) {
			return true;
		}
		if(fileName.endsWith(".dv")) {
			return true;
		}
		if(fileName.endsWith(".dv-avi")) {
			return true;
		}
		if(fileName.endsWith(".flv")) {
			return true;
		}
		if(fileName.endsWith(".f4v")) {
			return true;
		}
		if(fileName.endsWith(".h264")) {
			return true;
		}
		if(fileName.endsWith(".gvi")) {
			return true;
		}
		if(fileName.endsWith(".mov")) {
			return true;
		}
		if(fileName.endsWith(".mpe")) {
			return true;
		}
		if(fileName.endsWith(".mpeg1")) {
			return true;
		}
		if(fileName.endsWith(".mpeg4")) {
			return true;
		}
		if(fileName.endsWith(".mpg2")) {
			return true;
		}
		if(fileName.endsWith(".ogv")) {
			return true;
		}
		if(fileName.endsWith(".ogx")) {
			return true;
		}
		if(fileName.endsWith(".ogm")) {
			return true;
		}
		if(fileName.endsWith(".swf")) {
			return true;
		}
		if(fileName.endsWith(".webm")) {
			return true;
		}
		if(fileName.endsWith(".wmv")) {
			return true;
		}
		if(fileName.endsWith(".xvid")) {
			return true;
		}
		
		
		return false;
	}

	@Override
	public String getDescription() {
		
		return "supported media files";
	}
	
	
	public static boolean isImageFile(String fileName) {
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
		if(fileName.endsWith(".png")) {
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

}