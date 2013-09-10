package de.netprojectev.misc;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.Logger;
import org.jdesktop.swingx.util.GraphicsUtilities;

import com.sun.org.apache.bcel.internal.Constants;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.server.ConstantsServer;

/**
 * Class to hold global methods not containing to a specific other class.
 * 
 * @author samu
 */
public class Misc {

	private static final Logger log = LoggerBuilder.createLogger(Misc.class);

	/**
	 * handling the programs termination showing up a confirmation dialog and
	 * invokes the serialization
	 */
	public static void quit(final Component parent, final ClientMessageProxy proxy) {
		int exit = JOptionPane.showConfirmDialog(parent, "Are you sure you want to exit?", "Quit", JOptionPane.YES_NO_OPTION);
		if (exit == JOptionPane.YES_OPTION) {
			proxy.sendDisconnectRequest();
			/*
			 * try { saveToDisk(); ImageFile.threadPool.shutdownNow(); } catch
			 * (IOException e) {
			 * log.error("Error during saving settings and files.", e); }
			 */

			System.exit(0);
		}

	}

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

	public static void writeImageToDiskAsPNG(BufferedImage image, File path) {
		Iterator<ImageWriter> itereratorImageWriter = ImageIO.getImageWritersByFormatName("png");
		ImageWriter writer = (ImageWriter) itereratorImageWriter.next();
		ImageWriteParam writeParams = writer.getDefaultWriteParam();

		try {
			FileImageOutputStream fos = new FileImageOutputStream(path);
			writer.setOutput(fos);
			IIOImage img = new IIOImage((RenderedImage) image, null, null);
			writer.write(null, img, writeParams);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * reading current mouse pointer position
	 * 
	 * @return current mouse pointer position.
	 */
	public static Point currentMousePosition() {
		PointerInfo info = MouseInfo.getPointerInfo();
		return info.getLocation();
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
			Misc.savePropertiesToDisk(defProps, ConstantsServer.SAVE_PATH, ConstantsServer.FILENAME_DEFAULT_PROPERTIES);
		} catch (IOException e) {
			log.warn("Error during saving default properties to disk.", e);
		}

		return defProps;
	}

	public static Properties generateClientDefaultProps() {
		Properties defProps = new Properties();

		defProps.setProperty(ConstantsClient.PROP_PREVIEW_SCALE_WIDTH, "" + ConstantsClient.DEFAULT_PREVIEW_SCALE_WIDTH);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE, ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE + "pt");
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE, ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT);
		defProps.setProperty(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP, "" + ConstantsClient.DEFAULT_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP);

		try {
			Misc.savePropertiesToDisk(defProps, ConstantsClient.SAVE_PATH, ConstantsClient.FILENAME_DEFAULT_PROPERTIES);
		} catch (IOException e) {
			log.warn("Error during saving default properties to disk.", e);
		}

		return defProps;
	}

	public static ImageIcon getScaledImageIconFromBufImg(BufferedImage original, int widthToScaleTo) throws FileNotFoundException, IOException {

		ImageIcon scaled = new ImageIcon(Misc.getScaledImageInstanceFast(original, widthToScaleTo, (int) (widthToScaleTo * original.getHeight(null)) / original.getWidth(null)));
		original = null;

		return scaled;
	}

	public static ImageIcon getScaledImageIcon(ImageIcon original, int widthToScaleTo) throws FileNotFoundException, IOException {
		BufferedImage bi = imageIconToBufferedImage(original);
		ImageIcon scaled = new ImageIcon(Misc.getScaledImageInstanceFast(bi, widthToScaleTo, (int) (widthToScaleTo * bi.getHeight(null)) / bi.getWidth(null)));

		return scaled;
	}

	public static BufferedImage imageIconToBufferedImage(ImageIcon iconToConvert) {

		BufferedImage bi = createCompatibleTranslucentImage(iconToConvert.getIconWidth(), iconToConvert.getIconHeight());
		Graphics g = bi.createGraphics();
		iconToConvert.paintIcon(null, g, 0, 0);
		g.dispose();
		return bi;
	}

	/**
	 * This method scales a given {@link BufferedImage} and returns the scaled
	 * instance of it. This method is written in optimizing performance without
	 * big losings in quality. Especially downscaling is very highly optimized
	 * due to the use of {@link GraphicsUtilities}
	 * 
	 * @param imageToScale
	 *            the {@link BufferedImage} that should be scaled
	 * @param newWidth
	 *            the new width
	 * @param newHeight
	 *            the new height, if 0 then the height is calculated to keep the
	 *            aspect ratio
	 * @return the scaled image instance as {@link BufferedImage} in compatible
	 *         mode
	 */
	public static BufferedImage getScaledImageInstanceFast(BufferedImage imageToScale, int newWidth, int newHeight) {
		BufferedImage scaledImage;

		int oldWidth = imageToScale.getWidth();
		int oldHeight = imageToScale.getHeight();

		if (oldWidth > newWidth && oldHeight > newHeight) {
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

	// This method returns an image that is compatible with the
	// primary display device. If a user has multiple displays
	// with different depths, this may be suboptimal, but it

	// should work in the general case.
	public static GraphicsConfiguration getConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	// Creates a compatible image of the same dimension and
	// transparency as the given image
	public static BufferedImage createCompatibleImage(BufferedImage image) {
		return createCompatibleImage(image, image.getWidth(), image.getHeight());
	}

	// Creates a compatible image with the given width and
	// height that has the same transparency as the given image
	public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
		return getConfiguration().createCompatibleImage(width, height, image.getTransparency());
	}

	// Creates an opaque compatible image with the given
	// width and height
	public static BufferedImage createCompatibleImage(int width, int height) {
		return getConfiguration().createCompatibleImage(width, height);
	}

	// Creates a translucent compatible image with the given
	// width and height
	public static BufferedImage createCompatibleTranslucentImage(int width, int height) {
		return getConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}

	// Creates a compatible image from the content specified
	// by the resource
	public static BufferedImage loadCompatibleImage(File resource) throws IOException {
		BufferedImage image = ImageIO.read(resource);
		return toCompatibleImage(image);
	}

	// Creates and returns a new compatible image into which
	// the source image is copied

	// If the source image is already compatible, then the
	// source image is returned
	// This version takes a BufferedImage, but it could be
	// extended to take an Image instead
	public static BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = getConfiguration();
		if (image.getColorModel().equals(gc.getColorModel())) {
			return image;
		}
		BufferedImage compatibleImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics g = compatibleImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return compatibleImage;

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

	/**
	 * Restarts the currently running jar file using the process builder.
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void restartApplication(Class<?> clazz) throws URISyntaxException, IOException {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());

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
