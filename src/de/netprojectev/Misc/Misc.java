package de.netprojectev.Misc;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

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
			sizes[index] = Integer.toString(i) + "pt";
			index++;
		}
		return sizes;
	}
	
	/**
	 * Saves the given property object to the in the {@link Constants} specified path.
	 * 
	 * @param props the property object to save
	 * @throws IOException
	 */
	public synchronized static void savePropertiesToDisk(Properties props) throws IOException {
		FileWriter writer = new FileWriter(Constants.SAVE_PATH + Constants.FILENAME_PROPERTIES);
		props.store(writer, null);
		writer.close();
	}
	
	/**
	 * Reads properties from disk using the in the {@link Constants} defined path.
	 * 
	 * @return from disk loaded property object
	 * @throws IOException
	 */
	public synchronized static Properties loadPropertiesFromDisk() throws IOException {
		Properties props = new Properties();
		FileReader reader = new FileReader(Constants.SAVE_PATH + Constants.FILENAME_PROPERTIES);
		props.load(reader);
		reader.close();
		return props;
	}
	
	
	/**
	 * serializing a given object to the save path specified in the constants using the given filename.
	 * @param toSave a serializable object
	 * @param filename the filename it should be saved to 
	 * @throws IOException 
	 */
	public synchronized static void saveToFile(Serializable toSave, String filename) throws IOException {
		
		String path = Constants.SAVE_PATH;
		
		if(filename.isEmpty()) {		
			JOptionPane.showMessageDialog(null, "Error while saving files.", "Error", JOptionPane.ERROR_MESSAGE);
		}

		if(!new File(path).exists()) {
			new File(path).mkdirs();
		}
		

		FileOutputStream file = new FileOutputStream(path + filename);
		ObjectOutputStream o = new ObjectOutputStream(file);
		o.writeObject(toSave);
		o.close();
	}
	
	/**
	 * Deserializing a object from hard disk in save path with the given filename.
	 * @param filename file to load from disk
	 * @return deserialized object
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public synchronized static Object loadFromFile(String filename) throws IOException, ClassNotFoundException {
		
		Object fileToLoad = null;
		String path = Constants.SAVE_PATH;
		
		if(new File(path + filename).exists()) {

			FileInputStream file = new FileInputStream(path + filename);
			ObjectInputStream o = new ObjectInputStream(file);
			fileToLoad = o.readObject();
			o.close();

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
	
	/**
	 * Reads the default values from the {@link Constants} and then adds them to the generated default properties object.
	 *	 
	 * @return default properties
	 */
	public static Properties generateDefaultProps() {
		
		Properties defProps = new Properties();
		
		defProps.setProperty(Constants.PROP_PREVIEW_SCALE_WIDTH, "" + Constants.DEFAULT_PREVIEW_SCALE_WIDTH);
		defProps.setProperty(Constants.PROP_SCREEN_NUMBER_FULLSCREEN, "" + Constants.DEFAULT_SCREEN_NUMBER_FULLSCREEN);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR, "" + Constants.DEFAULT_TICKER_FONTCOLOR);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, "" + Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE + "pt");
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, "" + Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, "" + Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP);
		defProps.setProperty(Constants.PROP_TICKER_FONTCOLOR, "" + Constants.DEFAULT_TICKER_FONTCOLOR);
		defProps.setProperty(Constants.PROP_TICKER_FONTSIZE, "" + Constants.DEFAULT_TICKER_FONTSIZE);
		defProps.setProperty(Constants.PROP_TICKER_FONTTYPE, Constants.DEFAULT_TICKER_FONTTYPE);
		defProps.setProperty(Constants.PROP_TICKER_SPEED, "" + Constants.DEFAULT_TICKER_SPEED);

		return defProps;
	}
	
	public static Handler getLogFileHandlerAll() throws SecurityException, IOException {
		Handler logFileHandler = new FileHandler(Constants.SAVE_PATH + "log.txt", true);
		logFileHandler.setLevel(Level.ALL);
		logFileHandler.setFormatter(new SimpleFormatter());
		return logFileHandler;
	}
	
	public static Handler getLogFileHandlerError() throws SecurityException, IOException {
		Handler logFileHandler = new FileHandler(Constants.SAVE_PATH + "errorLog.txt", true);
		logFileHandler.setLevel(Level.SEVERE);
		logFileHandler.setFormatter(new SimpleFormatter());
		return logFileHandler;
	}
	
	
	/**
	 * Restarts the currently running jar file using the process builder.
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void restartApplication() throws URISyntaxException, IOException {
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
