package de.netprojectev.server.datastructures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import de.netprojectev.server.ConstantsServer;

public class VideoFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2033435655791192980L;
	private File videoFile;

	public VideoFile(String name, File videoFile) throws IOException {
		super(name, UUID.randomUUID()); // new Priority("Video", 0)
		Path srcPath = Paths.get(videoFile.getAbsolutePath());
		Path destPath = Paths.get(ConstantsServer.SAVE_PATH, ConstantsServer.CACHE_PATH_VIDEOS, this.id.toString());
		Files.move(srcPath, destPath, StandardCopyOption.ATOMIC_MOVE);
		this.videoFile = destPath.toFile();
	}

	public File getVideoFile() {
		return this.videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}

}
