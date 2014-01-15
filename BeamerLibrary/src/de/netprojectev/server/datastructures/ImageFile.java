package de.netprojectev.server.datastructures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.server.ConstantsServer;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;
	
	private transient byte[] image;
	private File pathOnDisk;

	public ImageFile(String name, Priority priority, byte[] imageAsBytes) {
		super(name, priority);
		this.pathOnDisk = new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_IMAGES + getId());
		this.image = null;
		
		try {
			Files.write(Paths.get(pathOnDisk.getAbsolutePath()), imageAsBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public byte[] get() throws IOException {
		if(image == null) {
			image = Files.readAllBytes(Paths.get(pathOnDisk.getAbsolutePath()));
		}
		return image;
	}
	
	public void clearMemory() {
		image = null;
	}

}
