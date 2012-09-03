package de.netprojectev.Media;

import java.awt.Image;
import java.io.File;
import java.util.Date;

import javax.swing.ImageIcon;

import de.netprojectev.Misc.Constants;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * Datastructure to deal with Image files from the hard disk.
 * 
 * @author samu
 * 
 */
public class ImageFile extends MediaFile {

	private static final long serialVersionUID = -6684164019970242002L;
	private String path;
	private transient ImageIcon preview;
	
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
		generatePreview();
	}

	/**
	 * generates a imageicon for previewing from path attribute of the object.
	 * The icon is read from hard disk and scaled in a new Thread, so the GUI does not block
	 */
	public void generatePreview() {
		
		new Thread() {
			@Override
			public void run() {
				
				if(path != null) {
					preview = new ImageIcon(path);
					preview = scaleIcon(preview);
				} else {
					corrupted = true;
					preview = null;
				}
			}
		}.start();
	}
	
	/**
	 * used to force a reload of the original image after scaling size was increased
	 */
	public void forceRealoadPreview() {
		generatePreview();
	}
	
	/**
     * 
     * @param preview the ImageIcon to scale
     * @return scaled preview instance of the given ImageIcon
     */
	private ImageIcon scaleIcon(ImageIcon preview) {	
    	preview.setImage(preview.getImage().getScaledInstance(Integer.parseInt(PreferencesHandler.getInstance().getProperties().getProperty(Constants.PROP_PREVIEW_SCALE_WIDTH)), -1,Image.SCALE_SMOOTH));  	
    	return preview;
    }

	@Override
	public void show() {
		display.getDisplayMainComponent().setImageToDraw(new File(path));
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

}
