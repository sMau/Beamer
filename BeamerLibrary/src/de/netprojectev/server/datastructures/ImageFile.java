package de.netprojectev.server.datastructures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.server.ConstantsServer;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;

	private transient byte[] image;
	private File pathOnDisk;

	//TODO change all io.File refs to the nio.Path way
	
	public ImageFile(String name, UUID priorityID, File pathOnDisk) throws IOException {
		super(name, priorityID);
		Path srcPath = Paths.get(pathOnDisk.getAbsolutePath());
		Path destPath = Paths.get(ConstantsServer.SAVE_PATH, ConstantsServer.CACHE_PATH_IMAGES, this.id.toString());
		Files.move(srcPath, destPath, StandardCopyOption.ATOMIC_MOVE);
		this.pathOnDisk = destPath.toFile();
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

	@Override
	public MediaType determineMediaType() {
		return MediaType.Image;
	}

	@Override
	public byte[] determinePreview() throws IOException {
		return get();
	}
}
