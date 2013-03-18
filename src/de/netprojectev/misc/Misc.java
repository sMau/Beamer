package de.netprojectev.misc;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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

import org.jdesktop.swingx.util.GraphicsUtilities;

import de.netprojectev.netty.testing2.Starter;
import de.netprojectev.old.ImageFile;
import de.netprojectev.old.ServerMediaFile;

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
	public static ServerMediaFile[] createMediaFromFiles(File[] files) {

		//TODO have to distinguish between videos and images. Maybe using my own ImageFileFilter
		
		ServerMediaFile[] mediaFiles = new ServerMediaFile[files.length];

		for(int i = 0; i < files.length; i++) {
			
			if(files[i].exists() && files[i].isFile()) {
				mediaFiles[i] = new ImageFile(files[i].getName(), files[i].getAbsolutePath(), Constants.DEFAULT_PRIORITY);
				
			}
			
		}
		
		return mediaFiles;
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
	
	public static String[] generateFontSizesCountdown() {
		int n = 4;
		int start = 36;
		int end = 140;
		int arraySize = 0;
		
		for(int i = start; i < end + 1;  i+= n) {
			arraySize++;
		}
		String[] sizes = new String[arraySize];
		int index = 0;
		for(int i = start; i < end + 1; i+=n) {
			sizes[index] = Integer.toString(i);
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
	public static void savePropertiesToDisk(Properties props, String savePath, String filename) throws IOException {
		
		if(!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}
		
		FileWriter writer = new FileWriter(savePath + filename);
		props.store(writer, null);
		writer.close();
	}
	
	/**
	 * Reads properties from disk using the in the {@link Constants} defined path.
	 * 
	 * @return from disk loaded property object
	 * @throws IOException
	 */
	public static Properties loadPropertiesFromDisk(String savePath, String filename) throws IOException {
		Properties props = new Properties();
		FileReader reader = new FileReader(savePath + filename);
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
	public static void saveToFile(Serializable toSave, String savePath, String filename) throws IOException {

		if(!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}
		

		FileOutputStream file = new FileOutputStream(savePath + filename);
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
	public static Object loadFromFile(String filename, String path) throws IOException, ClassNotFoundException {
		
		Object fileToLoad = null;
		
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
	public static synchronized String convertFromSecondsToTimeString(int seconds, boolean respectHours) {

		
		if(!respectHours) {
			
			int minutes = seconds / 60;
			int secondsLeft = seconds % 60;
			
			String minutesString = Integer.toString(minutes);
			if(minutesString.length() == 1) {
				minutesString = "0" + minutesString;
			}
			String secondsLeftString = Integer.toString(secondsLeft);
			if(secondsLeftString.length() == 1) {
				secondsLeftString = "0" + secondsLeftString;
			}
			
			return minutesString + ":" + secondsLeftString;
		} else {
			
			int hours = seconds / 3600;
			int minutes = (seconds % 3600) / 60;
			int secondsLeft = seconds % 60;
			
			String hoursString = Integer.toString(hours);
			if(hoursString.length() == 1) {
				hoursString = "0" + hoursString;
			}
			String minutesString = Integer.toString(minutes);
			if(minutesString.length() == 1) {
				minutesString = "0" + minutesString;
			}
			String secondsLeftString = Integer.toString(secondsLeft);
			if(secondsLeftString.length() == 1) {
				secondsLeftString = "0" + secondsLeftString;
			}
			
			return hoursString + ":" + minutesString + ":" + secondsLeftString;
			
			
		}
				
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
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE + "pt");
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, "" + Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT);
		defProps.setProperty(Constants.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, "" + Constants.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP);
		defProps.setProperty(Constants.PROP_TICKER_FONTCOLOR, "" + Constants.DEFAULT_TICKER_FONTCOLOR);
		defProps.setProperty(Constants.PROP_TICKER_FONTSIZE, "" + Constants.DEFAULT_TICKER_FONTSIZE);
		defProps.setProperty(Constants.PROP_TICKER_FONTTYPE, Constants.DEFAULT_TICKER_FONTTYPE);
		defProps.setProperty(Constants.PROP_TICKER_SPEED, "" + Constants.DEFAULT_TICKER_SPEED);
		defProps.setProperty(Constants.PROP_TICKER_SEPERATOR, "" + Constants.DEFAULT_TICKER_SEPERATOR);
		defProps.setProperty(Constants.PROP_COUNTDOWN_FONTCOLOR, "" + Constants.DEFAULT_COUNTDOWN_FONTCOLOR);
		defProps.setProperty(Constants.PROP_COUNTDOWN_FONTSIZE, "" + Constants.DEFAULT_COUNTDOWN_FONTSIZE);
		defProps.setProperty(Constants.PROP_COUNTDOWN_FONTTYPE, Constants.DEFAULT_COUNTDOWN_FONTTYPE);

		return defProps;
	}
	

	
	/**
	 * This method scales a given {@link BufferedImage} and returns the scaled instance of it.
	 * This method is written in optimizing performance without big losings in quality.
	 * Especially downscaling is very highly optimized due to the use of {@link GraphicsUtilities} 
	 * 
	 * @param imageToScale the {@link BufferedImage} that should be scaled
	 * @param newWidth the new width
	 * @param newHeight the new height
	 * @return the scaled image instance as {@link BufferedImage} in compatible mode
	 */
	public static BufferedImage getScaledImageInstanceFast(BufferedImage imageToScale, int newWidth, int newHeight) {
		
		BufferedImage scaledImage;
		
		int oldWidth = imageToScale.getWidth();
		int oldHeight = imageToScale.getHeight();
		
		if(oldWidth > newWidth && oldHeight > newHeight) {
			scaledImage = GraphicsUtilities.createThumbnail(imageToScale, newWidth, newHeight);
		} else {
			scaledImage = GraphicsUtilities.createCompatibleImage(newWidth, newHeight);
			Graphics2D g2 = scaledImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(imageToScale, 0, 0, newWidth, newHeight, null);
			g2.dispose();
		}
		
		return scaledImage;
	}
	
	/**
	 * 
	 * @param dir directory where search for image files should be started
	 * @return a {@link File} array of all image files in this and all subdirectories
	 */
	public static File[] searchForAllImageFiles(File dir) {
		ArrayList<File> allFiles = searchFile(dir);
		ArrayList<File> imageFiles = new ArrayList<File>();
		ImageFileFilter filter = new ImageFileFilter();
		
		for(int i = 0; i < allFiles.size(); i++) {
			if(filter.accept(allFiles.get(i))) {
				imageFiles.add(allFiles.get(i));
			}
			
		}
		return imageFiles.toArray(new File[imageFiles.size()]);	
	}
	
	
	/**
	 * Lists recursively all files in a directory.
	 * 
	 * @param dir directory the search should start at
	 * @return a complete list of all files in this and all subdirectories, excluded are the directories themselves
	 */
	private static ArrayList<File> searchFile(File dir) {

		File[] files = dir.listFiles();
		ArrayList<File> imageFiles = new ArrayList<File> ();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) { 
					imageFiles.add(files[i]);
				} else {
					imageFiles.addAll(searchFile(files[i])); 
				}
			}
		}
		return imageFiles;
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
