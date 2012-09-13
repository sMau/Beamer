package de.netprojectev.Media;

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

import de.netprojectev.GUI.Display.DisplayMainComponent;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;
import de.netprojectev.Preferences.PreferencesHandler;

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
		if(preview && displayImage) {
			imageFile.generateDisplayImage();
			imageFile.generatePreview();
		} else if(preview && !displayImage) {
			imageFile.generatePreview();
		} else if(!preview && displayImage) {
			imageFile.generateDisplayImage();
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
	public static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.NUMBER_OF_WORKER_THREADS);
	
	private String path;
	private transient ImageIcon preview;
	private transient BufferedImage loadedImage = null;
	
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
	 */
	protected void generatePreview() {
		log.log(Level.INFO, "generating new thumbnail for " + name);
		loadImageFileFromDisk();		
		int widthToScaleTo = Integer.parseInt(PreferencesHandler.getInstance().getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH));
		preview = new ImageIcon(Misc.getScaledImageInstanceFast(loadedImage, widthToScaleTo , (int) (widthToScaleTo * loadedImage.getHeight(null))/loadedImage.getWidth(null)));
	}

	/**
	 * loads the image from disk, if not already loaded before
	 */
	protected void loadImageFileFromDisk() {
		
		if(loadedImage == null && path != null) {
			log.log(Level.INFO, "loading image from hard disk " + name);
			try {
				loadedImage = GraphicsUtilities.loadCompatibleImage(new BufferedInputStream(new FileInputStream(path)));
			} catch (FileNotFoundException e) {
				log.log(Level.SEVERE, "imagefile could not be found on hard disk", e);
			} catch (IOException e) {
				log.log(Level.SEVERE, "error reading imagefile from hard disk", e);
			}
		}
	} 
		
	
	
	public void createNewImageInstances() {
		
		threadPool.execute(new ImageGenerationTask(this, true, true));

	}
	
	/**
	 * This method never should be called directly. It is only invoked from a worker thread doing these things in background.
	 * generates the scaled image object representation of the image file on the hard disk.
	 * it respects the current width and height of the {@link DisplayMainComponent}.
	 */
	protected void generateDisplayImage() {
		log.log(Level.INFO, "generating new display image object for " + name);
		DisplayMainComponent displayMainComp = display.getDisplayMainComponent();
		loadImageFileFromDisk();

		if (loadedImage != null) {
			int imW = loadedImage.getWidth(null);
			int imH = loadedImage.getHeight(null);
			displayImage = Misc.getScaledImageInstanceFast(loadedImage, (int) (displayMainComp.getHeight() * imW/imH), (int) displayMainComp.getHeight());
		}
		
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
