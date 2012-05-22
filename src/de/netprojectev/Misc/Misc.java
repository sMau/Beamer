package de.netprojectev.Misc;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.LiveTicker.TickerTextElement;
import de.netprojectev.Media.ImageFile;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * Class to hold global methods not containing to a specific other class.
 * @author samu 
 */
public class Misc {
	
	
	/**
	 * 
	 * @param files files to generate media file array from
	 * @return array of media files.
	 */
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
	
	/**
	 * 
	 * @param selectedIndices index array identifiying files in the mediahandler
	 * @return array of specified media files
	 */
	public static MediaFile[] indexListToMediaFiles(int[] selectedIndices) {
		
		MediaFile[] mediaFiles = new MediaFile[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			mediaFiles[i] = MediaHandler.getInstance().getMediaFiles().get(selectedIndices[i]); 
		}
		
		return mediaFiles;
	}
	
	//TODO generic implemenation for these two methodes
	/**
	 * 
	 * @param selectedIndices index array identifiying priorities in the preferences handler
	 * @return array of specified priorities
	 */
	public static Priority[] indexListToPriorities(int[] selectedIndices) {
		
		Priority[] priorities = new Priority[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			priorities[i] = PreferencesHandler.getInstance().getListOfPriorities().get(selectedIndices[i]);
			
		}
		
		return priorities;
	}
	
	//second method for generic implementation
	/**
	 * 
	 * @param selectedIndices index array identifiying themes in the preferences handler
	 * @return array of specified themes
	 */
	public static Theme[] indexListToThemes(int[] selectedIndices) {
		
		Theme[] themes = new Theme[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			themes[i] = PreferencesHandler.getInstance().getListOfThemes().get(selectedIndices[i]);
			
		}
		
		return themes;
	}
	
	/**
	 * 
	 * @param selectedIndices index array identifiying ticker text elements in the Liveticker 
	 * @return array of specified ticker text elements
	 */
	public static TickerTextElement[] indexListToTickerElts(int[] selectedIndices) {
		
		TickerTextElement[] elements = new TickerTextElement[selectedIndices.length];
		
		for(int i = 0; i < selectedIndices.length; i++) {
			
			elements[i] = LiveTicker.getInstance().getTextElements().get(selectedIndices[i]);
			
		}
		
		return elements;
	}
	
	/**
	 * reading current mouse pointer position
	 * @return current mouse pointer position.
	 */
	public static Point currentMousePosition() {
		PointerInfo info = MouseInfo.getPointerInfo();
		return info.getLocation();
	}
	
	/**
	 * Generates font sizes based on stepwidth n, start and end size.
	 * @return array of possible sizes
	 */
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
	
	/**
	 * serializing a given object to the save path specified in the constants using the given filename.
	 * @param toSave a serializable object
	 * @param filename the filename it should be saved to 
	 */
	public synchronized static void saveToFile(Serializable toSave, String filename) {
		
		String path = Constants.SAVE_PATH;
		
		if(filename.isEmpty() || filename == null) {		
			filename = "undefined.bmr";
			//TODO Error dialog
		}

		if(!new File(path).exists()) {
			new File(path).mkdirs();
		}
		
		try {
			FileOutputStream file = new FileOutputStream(path + filename);
			ObjectOutputStream o = new ObjectOutputStream(file);
			o.writeObject(toSave);
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
			//TODO Error Dialog
		}

	}
	
	/**
	 * Deserializing a object from hard disk in save path with the given filename.
	 * @param filename file to load from disk
	 * @return deserialized object
	 */
	public synchronized static Object loadFromFile(String filename) {
		
		Object fileToLoad = null;
		String path = Constants.SAVE_PATH;
		
		if(new File(path + filename).exists()) {
			try {
				FileInputStream file = new FileInputStream(path + filename);
				ObjectInputStream o = new ObjectInputStream(file);
				fileToLoad = o.readObject();
				o.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				//TODO Error Dialog
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				//TODO Error Dialog
			}

			return fileToLoad;
		} else {
			return null;
		}
		
		
	}

}
