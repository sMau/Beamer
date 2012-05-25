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
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.LiveTicker.TickerTextElement;
import de.netprojectev.Main.Starter;
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
			JOptionPane.showMessageDialog(null, "Error while saving files.", "Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Error while saving files.", "Error", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "Error while loading files.", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error while loading files.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			return fileToLoad;
		} else {
			return null;
		}
		
		
	}
	
	/**
	 * Converts seconds to a formatted String showing minutes and seconds sperated by ":"
	 * @param seconds seconds to convert
	 * @return a formatted string like mm:ss
	 */
	public static synchronized String convertFromSecondsToTimeString(int seconds) {

		int minutes = seconds / 60;
		String minutesString = Integer.toString(minutes);
		if(minutesString.length() == 1) {
			minutesString = "0" + minutesString;
		}
		
		int secondsLeft = seconds % 60;
		String secondsLeftString = Integer.toString(secondsLeft);
		if(secondsLeftString.length() == 1) {
			secondsLeftString = "0" + secondsLeftString;
		}
		
		return minutesString + ":" + secondsLeftString;
	}
	
	
	
	public static void restartApplication() throws URISyntaxException,
			IOException {
		final String javaBin = System.getProperty("java.home") + File.separator
				+ "bin" + File.separator + "java";
		final File currentJar = new File(Starter.class.getProtectionDomain()
				.getCodeSource().getLocation().toURI());

		if (!currentJar.getName().endsWith(".jar"))
			return;

		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());
		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}
	


}
