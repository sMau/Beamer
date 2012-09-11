package de.netprojectev.Media;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	
	public static ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
	
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
	 */
	protected void generatePreview() {
		
		if(path != null) {
			preview = new ImageIcon(path);
			preview = scaleIcon(preview);
		} else {
			corrupted = true;
			preview = null;
		}
		
	}
	
	public void createNewImageInstances() {
		
		threadPool.execute(new ImageGenerationTask(this, true, true));

	}
	
	protected void generateDisplayImage() {
		
		//TODO the scaling is incorrect when theres e.g. a aspect ratio of type x:y, x<y
		
		DisplayMainComponent displayMainComp = display.getDisplayMainComponent();
		InputStream in;
		BufferedImage intermediate = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			intermediate = GraphicsUtilities.loadCompatibleImage(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if (intermediate != null) {
			int imW = intermediate.getWidth(null);
			int imH = intermediate.getHeight(null);
			
			if(displayMainComp.getWidth()/displayMainComp.getHeight() <= imW/imH) {
				displayImage = Misc.getScaledImageInstanceFast(intermediate, (int) (displayMainComp.getHeight() * imW/imH), (int) displayMainComp.getHeight());
			} else {
				displayImage = Misc.getScaledImageInstanceFast(intermediate, (int) displayMainComp.getWidth(), (int) (displayMainComp.getWidth() * imH/imW));

			}
			
		}
		
	}
	
	/**
	 * used to force a reload of the original image after scaling size was increased
	 */
	public void forceRealoadPreview() {
		threadPool.execute(new ImageGenerationTask(this, true, false));
	}
	
	public void forceRealodDisplayImage() {
		threadPool.execute(new ImageGenerationTask(this, false, true));
	}
	
	/**
     * 
     * @param preview the ImageIcon to scale
     * @return scaled preview instance of the given ImageIcon
     */
	private ImageIcon scaleIcon(ImageIcon preview) {	
		
		//TODO fix performance -> do not user getScaledInstance, use progressive bilinear scaling (filthy rich clients)
    	preview.setImage(preview.getImage().getScaledInstance(Integer.parseInt(PreferencesHandler.getInstance().getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH)), -1,Image.SCALE_SMOOTH));  	
    	return preview;
    }

	@Override
	public void show() {
		display.getDisplayMainComponent().setImageToDraw(displayImage);
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
