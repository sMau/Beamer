package de.netprojectev.server.datastructures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import de.netprojectev.server.ConstantsServer;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;

	private transient byte[] image;
	private File pathOnDisk;

	public ImageFile(String name, UUID priorityID, File pathOnDisk) {
		super(name, priorityID);
		this.pathOnDisk.renameTo(new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_IMAGES + getId()));
		this.image = null;
	}

	public void clearMemory() {
		this.image = null;
	}

	public byte[] get() throws IOException {
		if (this.image == null) {
			this.image = Files.readAllBytes(Paths.get(this.pathOnDisk.getAbsolutePath()));
		}
		return this.image;
	}

}
