package de.netprojectev.Media;

import java.io.File;
import java.util.Date;

import javax.swing.ImageIcon;

import de.netprojectev.Misc.Constants;

/**
 * Datastructure to deal with Image files from the hard disk.
 * 
 * @author samu
 * 
 */
public class ImageFile extends MediaFile {

	private static final long serialVersionUID = -6684164019970242002L;
	private String path;
	private transient ImageIcon preview; //TODO Make imageicons serializable throwin exception if cannot be loaded cause of no image file
	
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
	 */
	public void generatePreview() {
		if(path != null) {
			this.preview = new ImageIcon(path);
		} else {
			this.preview = null;
		}

	}

	@Override
	public void show() {
		System.out.println("Show file: " + path + "   " + new Date());
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
