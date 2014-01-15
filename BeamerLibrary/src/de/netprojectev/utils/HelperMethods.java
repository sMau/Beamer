package de.netprojectev.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;

import com.sun.org.apache.bcel.internal.Constants;

import de.netprojectev.server.ConstantsServer;

/**
 * Class to hold global methods not containing to a specific other class.
 * 
 * @author samu
 */
public class HelperMethods {

	private static final Logger log = LoggerBuilder.createLogger(HelperMethods.class);

	public static boolean isIpAddress(final String ip) {

		if(ip.equals("localhost")) {
			return true;
		}
		
		String PATTERN = 
				"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + 
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + 
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * Generates font sizes based on stepwidth n, start and end size.
	 * 
	 * @return array of possible sizes
	 */
	public static String[] generateFontSizes() {
		int n = 4;
		int start = 12;
		int end = 80;
		int arraySize = 0;

		for (int i = start; i < end + 1; i += n) {
			arraySize++;
		}
		String[] sizes = new String[arraySize];
		int index = 0;
		for (int i = start; i < end + 1; i += n) {
			sizes[index] = Integer.toString(i);
			index++;
		}
		return sizes;
	}

	public static String[] generateFontSizesCountdown() {
		int n = 4;
		int start = 36;
		int end = 140;
		int arraySize = 0;

		for (int i = start; i < end + 1; i += n) {
			arraySize++;
		}
		String[] sizes = new String[arraySize];
		int index = 0;
		for (int i = start; i < end + 1; i += n) {
			sizes[index] = Integer.toString(i);
			index++;
		}
		return sizes;
	}

	/**
	 * Saves the given property object to the in the Constants specified path.
	 * 
	 * @param props
	 *            the property object to save
	 * @throws IOException
	 */
	public static void savePropertiesToDisk(Properties props, String savePath, String filename) throws IOException {

		if (!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}

		FileWriter writer = new FileWriter(savePath + filename);
		props.store(writer, null);
		writer.close();
	}

	/**
	 * Reads properties from disk using the in the {@link Constants} defined
	 * path.
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
	 * serializing a given object to the save path specified in the constants
	 * using the given filename.
	 * 
	 * @param toSave
	 *            a serializable object
	 * @param filename
	 *            the filename it should be saved to
	 * @throws IOException
	 */
	public static void saveToFile(Serializable toSave, String savePath, String filename) throws IOException {

		if (!new File(savePath).exists()) {
			new File(savePath).mkdirs();
		}

		FileOutputStream file = new FileOutputStream(savePath + filename);
		ObjectOutputStream o = new ObjectOutputStream(file);
		o.writeObject(toSave);
		o.close();
	}

	/**
	 * Deserializing a object from hard disk in save path with the given
	 * filename.
	 * 
	 * @param filename
	 *            file to load from disk
	 * @return deserialized object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Serializable loadFromFile(String filename, String path) throws IOException, ClassNotFoundException {

		Object fileToLoad = null;

		if (new File(path + filename).exists()) {

			FileInputStream file = new FileInputStream(path + filename);
			ObjectInputStream o = new ObjectInputStream(file);
			fileToLoad = o.readObject();
			o.close();

			return (Serializable) fileToLoad;
		} else {
			return null;
		}

	}

	/**
	 * Converts seconds to a formatted String showing minutes and seconds
	 * sperated by ":"
	 * 
	 * @param seconds
	 *            seconds to convert
	 * @return a formatted string like mm:ss
	 */
	public static synchronized String convertFromSecondsToTimeString(long seconds, boolean respectHours) {

		if (!respectHours) {

			long minutes = seconds / 60;
			long secondsLeft = seconds % 60;

			String minutesString = Long.toString(minutes);
			if (minutesString.length() == 1) {
				minutesString = "0" + minutesString;
			}
			String secondsLeftString = Long.toString(secondsLeft);
			if (secondsLeftString.length() == 1) {
				secondsLeftString = "0" + secondsLeftString;
			}

			return minutesString + ":" + secondsLeftString;
		} else {

			long hours = seconds / 3600;
			long minutes = (seconds % 3600) / 60;
			long secondsLeft = seconds % 60;

			String hoursString = Long.toString(hours);
			if (hoursString.length() == 1) {
				hoursString = "0" + hoursString;
			}
			String minutesString = Long.toString(minutes);
			if (minutesString.length() == 1) {
				minutesString = "0" + minutesString;
			}
			String secondsLeftString = Long.toString(secondsLeft);
			if (secondsLeftString.length() == 1) {
				secondsLeftString = "0" + secondsLeftString;
			}

			return hoursString + ":" + minutesString + ":" + secondsLeftString;

		}

	}

	public static Properties generateServerDefaultProps() {

		Properties defProps = new Properties();

		defProps.setProperty(ConstantsServer.PROP_PW, ConstantsServer.DEFAULT_PW);
		defProps.setProperty(ConstantsServer.PROP_HEARTBEAT_INTERVALL, ConstantsServer.DEFAULT_HEARTBEAT_INTERVALL);

		defProps.setProperty(ConstantsServer.PROP_GENERAL_BACKGROUND_COLOR, "" + ConstantsServer.DEFAULT_GENERAL_BACKGROUND_COLOR);

		defProps.setProperty(ConstantsServer.PROP_TICKER_FONTCOLOR, "" + ConstantsServer.DEFAULT_TICKER_FONTCOLOR);
		defProps.setProperty(ConstantsServer.PROP_TICKER_FONTSIZE, "" + ConstantsServer.DEFAULT_TICKER_FONTSIZE);
		defProps.setProperty(ConstantsServer.PROP_TICKER_FONTTYPE, ConstantsServer.DEFAULT_TICKER_FONTTYPE);
		defProps.setProperty(ConstantsServer.PROP_TICKER_SPEED, "" + ConstantsServer.DEFAULT_TICKER_SPEED);
		defProps.setProperty(ConstantsServer.PROP_TICKER_SEPERATOR, "" + ConstantsServer.DEFAULT_TICKER_SEPERATOR);
		defProps.setProperty(ConstantsServer.PROP_TICKER_BACKGROUND_COLOR, "" + ConstantsServer.DEFAULT_TICKER_BACKGROUND_COLOR);
		defProps.setProperty(ConstantsServer.PROP_TICKER_BACKGROUND_ALPHA, "" + ConstantsServer.DEFAULT_TICKER_BACKGROUND_ALPHA);
		defProps.setProperty(ConstantsServer.PROP_COUNTDOWN_FONTCOLOR, "" + ConstantsServer.DEFAULT_COUNTDOWN_FONTCOLOR);
		defProps.setProperty(ConstantsServer.PROP_COUNTDOWN_FONTSIZE, "" + ConstantsServer.DEFAULT_COUNTDOWN_FONTSIZE);
		defProps.setProperty(ConstantsServer.PROP_COUNTDOWN_FONTTYPE, ConstantsServer.DEFAULT_COUNTDOWN_FONTTYPE);

		try {
			HelperMethods.savePropertiesToDisk(defProps, ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_DEFAULT_PROPERTIES);
		} catch (IOException e) {
			log.warn("Error during saving default properties to disk.", e);
		}

		return defProps;
	}

	

	/**
	 * 
	 * @param dir
	 *            directory where search for image files should be started
	 * @return a {@link File} array of all image files in this and all
	 *         subdirectories
	 */
	public static File[] searchForAllImageFiles(File dir) {
		ArrayList<File> allFiles = searchFile(dir);
		ArrayList<File> imageFiles = new ArrayList<File>();
		MediaFileFilter filter = new MediaFileFilter();

		for (int i = 0; i < allFiles.size(); i++) {
			if (filter.accept(allFiles.get(i))) {
				imageFiles.add(allFiles.get(i));
			}

		}
		return imageFiles.toArray(new File[imageFiles.size()]);
	}

	/**
	 * Lists recursively all files in a directory.
	 * 
	 * @param dir
	 *            directory the search should start at
	 * @return a complete list of all files in this and all subdirectories,
	 *         excluded are the directories themselves
	 */
	private static ArrayList<File> searchFile(File dir) {

		File[] files = dir.listFiles();
		ArrayList<File> imageFiles = new ArrayList<File>();
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



}
