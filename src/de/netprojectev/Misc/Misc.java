package de.netprojectev.Misc;

import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.io.File;

import de.netprojectev.GUI.Preferences.PreferencesFrame;
import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.LiveTicker.TickerTextElement;
import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * 
 * Hier werden nirgendwo sonst hinpassende globale Schnittstellen angeboten
 */
public class Misc {
	
	
	
	public static MediaFile[] createMediaFromFiles(File[] files) {
		
		MediaFile[] mediaFiles = new MediaFile[files.length];
		
		//TODO im Moment dumme for schleife; soll in Zukunft automatisch zwischen Video und Bild unterscheiden k�nnen und die entsprechende MedienDatei erzeugen
		
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
	
	//TODO generic implemenation for these two methodes
	public static Priority[] indexListToPriorities(int[] selectedIndices) {
		
		Priority[] priorities = new Priority[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			priorities[i] = PreferencesHandler.getInstance().getListOfPriorities().get(selectedIndices[i]);
			
		}
		
		return priorities;
	}
	//second method for generic implementation
	public static Theme[] indexListToThemes(int[] selectedIndices) {
		
		Theme[] themes = new Theme[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			themes[i] = PreferencesHandler.getInstance().getListOfThemes().get(selectedIndices[i]);
			
		}
		
		return themes;
	}
	
	public static TickerTextElement[] indexListToTickerElts(int[] selectedIndices) {
		
		TickerTextElement[] elements = new TickerTextElement[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			elements[i] = LiveTicker.getInstance().getTextElements().get(selectedIndices[i]);
			
		}
		
		return elements;
	}
	
	public static Point currentMousePosition() {
		PointerInfo info = MouseInfo.getPointerInfo();
		return info.getLocation();
	}
	
	public static String[] generateFontSizes() {
		int n = 4;
		int start = 12;
		int end = 80;
		int arraySize = 0;
		
		for(int i = start; i < end + 1;  i+= n) {
			arraySize++;
		}
		String[] sizes = new String[arraySize];
		int index = 0;
		for(int i = start; i < end + 1; i+=n) {
			sizes[index] = Integer.toString(i) + "px";
			index++;
		}
		return sizes;
	}

}
