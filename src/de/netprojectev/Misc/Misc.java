package de.netprojectev.Misc;

import java.io.File;

import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.MediaHandler.MediaHandler;

/**
 * 
 * Hier werden nirgendwo sonst hinpassende globale Schnittstellen angeboten
 */
public class Misc {
	
	
	public static MediaFile[] createMediaFromFiles(File[] files) {
		
		MediaFile[] mediaFiles = new MediaFile[files.length];
		
		//TODO im Moment dumme for schleife; soll in Zukunft automatisch zwischen Video und Bild unterscheiden können und die entsprechende MedienDatei erzeugen
		
		for(int i = 0; i < files.length; i++) {
			
			if(files[i].exists() && files[i].isFile()) {
				mediaFiles[i] = new ImageFile(files[i].getName(), files[i].getAbsolutePath(), Constants.DEFAULT_PRIORITY);
				
			}
			
		}
		
		return mediaFiles;
	}
	
	
	public static MediaFile[] indexListToMediaFiles(int[] selectedIndices) {
		
		MediaFile[] mediaFiles = new MediaFile[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			mediaFiles[i] = MediaHandler.getInstance().getMediaFiles().get(selectedIndices[i]); 
		}
		
		return mediaFiles;
	}
}
