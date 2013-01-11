package de.netprojectev.media;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.jdesktop.swingx.util.GraphicsUtilities;

import de.netprojectev.gui.display.DisplayMainComponent;
import de.netprojectev.misc.Constants;
import de.netprojectev.misc.Misc;
import de.netprojectev.preferences.PreferencesHandler;

/**
 * Datastructure to deal with Image files from the hard disk.
 * 
 * @author samu
 * 
 */

class ImageGenerationTask implements Runnable {

	private ImageFile imageFile;
	private boolean preview;
	private boolean displayImage;
	
	public ImageGenerationTask(ImageFile imageFile, boolean preview, boolean displayImage) {
		super();
		this.imageFile = imageFile;
		this.preview = preview;
		this.displayImage = displayImage;
	}
	
	@Override
	public void run() {
		BufferedImage loadedImage;
		try {
			loadedImage = imageFile.loadImageFileFromDisk();
			if(preview && displayImage) {	
				imageFile.generateDisplayImage(loadedImage);
				imageFile.generatePreview(loadedImage);
			} else if(preview && !displayImage) {
				imageFile.generatePreview(loadedImage);
			} else if(!preview && displayImage) {
				imageFile.generateDisplayImage(loadedImage);
			}
		} catch (IOException e) {
			Misc.getLoggerAll(ImageFile.class.getName()).log(Level.SEVERE, "IOException during image object generation", e);
		}
		
	}

}

public class ImageFile extends MediaFile {

	private static final long serialVersionUID = -6684164019970242002L;
	
	private static final Logger log = Misc.getLoggerAll(ImageFile.class.getName());
	//TODO Last worked here
	/*
	 * fix the outof memory exception things when importing huge amount of images at one time
	 */
	public static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
	
	private String path;
	private transient ImageIcon preview;
	
	private transient BufferedImage displayImage;
	
	/**
	 * 
	 * @param name name in the manager
	 * @param path path to the file on disk
	 * @param priority initial priority
	 */
	public ImageFile(String name, String path, Priority priority) {
		super(name, priority);
		this.path = path;
		this.priority = Constants.DEFAULT_PRIORITY;
		createNewImageInstances();
	}

	/**
	 * generates a imageicon for previewing from path attribute of the object.
	 * The icon is read from hard disk and scaled in a new Thread, so the GUI does not block
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected void generatePreview(BufferedImage loadedImage) throws FileNotFoundException, IOException {
		log.log(Level.INFO, "generating new thumbnail for " + name);
		int widthToScaleTo = Integer.parseInt(PreferencesHandler.getInstance().getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH));
		preview = new ImageIcon(Misc.getScaledImageInstanceFast(loadedImage, widthToScaleTo , (int) (widthToScaleTo * loadedImage.getHeight(null))/loadedImage.getWidth(null)));
		loadedImage = null;
	}

	/**
	 * loads the image from disk, if not already loaded before
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected BufferedImage loadImageFileFromDisk() throws FileNotFoundException, IOException {
		if(path != null) {
			log.log(Level.INFO, "loading image from hard disk " + name);
			return GraphicsUtilities.loadCompatibleImage(new BufferedInputStream(new FileInputStream(path)));
		}
		return null;
	} 
		
	
	
	public void createNewImageInstances() {
		
		threadPool.execute(new ImageGenerationTask(this, true, true));

	}
	
	/**
	 * This method never should be called directly. It is only invoked from a worker thread doing these things in background.
	 * generates the scaled image object representation of the image file on the hard disk.
	 * it respects the current width and height of the {@link DisplayMainComponent}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	protected void generateDisplayImage(BufferedImage loadedImage) throws FileNotFoundException, IOException {
		log.log(Level.INFO, "generating new display image object for " + name);
		DisplayMainComponent displayMainComp = display.getDisplayMainComponent();

		int imW = loadedImage.getWidth(null);
		int imH = loadedImage.getHeight(null);
		displayImage = Misc.getScaledImageInstanceFast(loadedImage, (int) (displayMainComp.getHeight() * imW/imH), (int) displayMainComp.getHeight());
		loadedImage = null;
		
	}
	
	/**
	 * forces a regeneration of the preview icon with the new scale parameters
	 */
	public void forceRealoadPreview() {
		threadPool.execute(new ImageGenerationTask(this, true, false));
	}
	
	/**
	 * forces a regeneration of the display image icon with the new scale parameters
	 */
	public void forceRealodDisplayImage() {
		threadPool.execute(new ImageGenerationTask(this, false, true));
	}

	@Override
	public void show() {
		log.log(Level.INFO, "showing image " + name);
		display.getDisplayMainComponent().drawImage(displayImage);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

	public BufferedImage getDisplayImage() {
		return displayImage;
	}

	public void setDisplayImage(BufferedImage displayImage) {
		this.displayImage = displayImage;
	}

}
